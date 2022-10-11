package com.casper.sdk.types.cltypes


import com.casper.sdk.util.{ByteUtils, HexUtils}
import com.casper.sdk.types.cltypes.*
import com.casper.sdk.types.cltypes.CLPublicKey
import com.casper.sdk.types.cltypes.CLTypeInfo.serializeCLTypes
import io.circe.Decoder.{Result, decodeList}
import io.circe.HCursor

import java.nio.charset.StandardCharsets
import scala.collection.immutable.List
import scala.collection.mutable.ArrayBuilder
import scala.math.BigInt.int2bigInt
import scala.util.{Failure, Success, Try}

/**
 * CLValue : datas used by smart contracts.
 *
 * @param bytes
 * @param cl_type
 * @param parsed
 */
case class CLValue(
                    bytes: Array[Byte],
                    cl_infoType: CLTypeInfo,
                    parsed: Any
                  ) {

  /**
   * Constructor using a Byte Array and a cltype
   *
   * @param bytes
   * @param cl_Type
   */
  def this(bytes: Array[Byte], cl_Type: CLType) = this(bytes, CLTypeInfo(cl_Type), null)

  /**
   * Constructor using a hex String, cltype and a parsed value
   *
   * @param hexBytes
   * @param clType
   * @param parsed
   */
  def this(hexBytes: String, clType: CLTypeInfo, parsed: Any) = this(HexUtils.fromHex(hexBytes).getOrElse(Array.emptyByteArray), clType, parsed)

}

//companion object
object CLValue {


  def getBytes(cLValueOption: Option[CLValue]): Array[Byte] = cLValueOption.map(v => v.bytes).getOrElse(Array.emptyByteArray)


  /**
   * Circe decoding-encoding
   */

  import io.circe.{Decoder, Encoder, HCursor, Json}
  import io.circe.cursor._


  //encoder

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

  implicit val encoder: Encoder[CLValue] = new Encoder[CLValue] {
    final def apply(value: CLValue): Json = Json.obj(
      ("cl_type", value.cl_infoType.asJson),
      ("bytes", Json.fromString(HexUtils.toHex(value.bytes).getOrElse(""))),
      ("parsed", encodeParsed(value))
    )
  }

  /**
   * encode parsed value
   *
   * @param value
   * @return Json
   */
  def encodeParsed(value: CLValue): Json = value.parsed match {
    case array: Array[Any] => encodeParsedForArray(array)
    case null => Json.Null
    case _ => Json.fromString(value.parsed.toString)
    //TODO case of Object
  }

  /**
   * encode parsed for Arrays
   *
   * @param array
   * @return Json
   */
  def encodeParsedForArray(array: Array[Any]): Json = {
    if (array.length > 0) {
      val parsed = new StringBuilder("[")
      for (k <- 0 to array.length - 1) {
        if (k < array.length - 1)
          parsed.append("\"").append(array(k.toInt)).append("\",")
        else
          parsed.append("\"").append(array(array.length - 1)).append("\"")
      }
      parsed.append("]")
      Json.fromString(parsed.toString())
    }
    else
      Json.fromString("")
  }


  implicit val decoder: Decoder[CLValue] = new Decoder[CLValue] {
    final def apply(c: HCursor): Decoder.Result[CLValue] =
      for {
        bytesNode <- c.downField("bytes").as[String]
        typeNode <- c.downField("cl_type").as[HCursor]
        parsedNode <- c.downField("parsed").as[Json]
      } yield {
        val clTypeInfo = cLTypeInfo(typeNode)
        val parsedValue = decodeParsed(parsedNode, clTypeInfo)

        clTypeInfo match {
          case clTypeInfo: CLOptionTypeInfo => new CLOptionValue(bytesNode, clTypeInfo, parsedValue)
          case _ => new CLValue(bytesNode, clTypeInfo, parsedValue)
        }
      }
  }


  /**
   * compute CLTypeInfo
   *
   * @param typeNode
   * @return CLTypeInfo
   */
  def cLTypeInfo(typeNode: HCursor): CLTypeInfo = {
    val cl_Type = clType(typeNode)
    cl_Type match {
      case CLType.ByteArray => new CLByteArrayTypeInfo(typeNode.downField(CLType.ByteArray.toString).as[Int].getOrElse(0))
      case CLType.Option => {
        val optionNode = typeNode.downField(CLType.Option.toString).asInstanceOf[HCursor]
        val interType = cLTypeInfo(optionNode)
        new CLOptionTypeInfo(interType)
      }
      case CLType.List => {
        val listNode = typeNode.downField(CLType.List.toString).asInstanceOf[HCursor]
        val interType = cLTypeInfo(listNode)
        new CLListTypeInfo(interType)
      }
      case _ => CLTypeInfo(cl_Type)
    }
  }

  /**
   * Compute CLType
   *
   * @param typeNode
   * @return CLType
   */
  def clType(typeNode: HCursor): CLType = typeNode.value.name match {
    case "String" => CLType.valueOf(typeNode.value.asString.getOrElse(""))
    case "Object" => CLType.valueOf(typeNode.value.asObject.get.keys.toList(0))
    case _ => CLType.valueOf(typeNode.downN(0).as[String].toString)
  }


  /**
   * Decode parsed value
   *
   * @param parsedNode
   * @param clTypeInfo
   * @return Any
   */
  def decodeParsed(parsedNode: Json, clTypeInfo: CLTypeInfo): Any = parsedNode.name match {
    case "String" => parsedNode.asString.getOrElse("")
    case "Number" => parsedNode.asNumber.getOrElse("0")
    case "Array" => decodeParsedArray(parsedNode, clTypeInfo)
    case "Object" => decodeParsedObject(parsedNode, clTypeInfo)
    case "Null" => null
  }


  /**
   * Decode parsed of type Object
   *
   * @param obj
   * @param clTypeInfo
   * @return Any
   */
  def decodeParsedObject(obj: Json, clTypeInfo: CLTypeInfo): Any =

    clTypeInfo.cl_Type match {
      case CLType.Key => obj.asObject.get.keys.toList(0) match {
        case "Account" => obj.asObject.get.values.toList(0).as[AccountHash].getOrElse(Json.Null)
        case "URef" => obj.asObject.get.values.toList(0).as[URef].getOrElse(Json.Null)
        case _ => obj
      }
      case _ => obj
    }


  /**
   * decode parsed value
   *
   * @param typeNode
   * @param clTypeInfo
   * @return
   */
  def decodeParsedArray(array: Json, clTypeInfo: CLTypeInfo): Any = clTypeInfo match {
    case clList: CLListTypeInfo => clList.cltypeInfo.cl_Type match {
      case CLType.Map => array.as[Seq[Seq[Map[String, Json]]]].getOrElse(Seq.empty)
      case _ => array.as[Seq[String]].getOrElse(Seq.empty)
    }
    case _ => Json.Null
  }

  /*Factory methods*/


  //Bool
  def Bool(value: Boolean): Option[CLValue] = Try {
    val bytes = Array[Byte]((if (value) 0x01 else 0x00).toByte)
    new CLValue(bytes, CLTypeInfo(CLType.Bool), value);
  }.toOption

  //Unit
  def Unit(): Option[CLValue] = Try(new CLValue(Array.empty[Byte], CLTypeInfo(CLType.Unit), null)).toOption

  //U8
  def U8(value: Byte): Option[CLValue] = Try(new CLValue(new Array[Byte](value), CLTypeInfo(CLType.U8), value)).toOption

  //I32
  def I32(value: BigInt): Option[CLValue] = Try(new CLValue(ByteUtils.serializeFixedWidthNumber(value, 4).get, CLTypeInfo(CLType.I32), value)).toOption

  //U32
  def U32(value: BigInt): Option[CLValue] = Try(new CLValue(ByteUtils.serializeFixedWidthNumber(value, 4).get, CLTypeInfo(CLType.U32), value)).toOption

  //U64
  def U64(value: BigInt): Option[CLValue] = Try(new CLValue(ByteUtils.serializeFixedWidthNumber(value, 8).get, CLTypeInfo(CLType.U64), value)).toOption

  //I64
  def I64(value: BigInt): Option[CLValue] = Try(new CLValue(ByteUtils.serializeFixedWidthNumber(value, 8).get, CLTypeInfo(CLType.I64), value)).toOption

  //U128
  def U128(value: BigInt): Option[CLValue] = Try(new CLValue(ByteUtils.serializeArbitraryWidthNumber(value, 16).get, CLTypeInfo(CLType.U128), value)).toOption

  //U128
  def U256(value: BigInt): Option[CLValue] = Try(new CLValue(ByteUtils.serializeArbitraryWidthNumber(value, 32).get, CLTypeInfo(CLType.U256), value)).toOption

  //U512
  def U512(value: BigInt): Option[CLValue] = Try(new CLValue(ByteUtils.serializeArbitraryWidthNumber(value, 64).get, CLTypeInfo(CLType.U512), value)).toOption

  //String
  def String(value: String): Option[CLValue] = Try(new CLValue(ByteUtils.join(U32(value.getBytes(StandardCharsets.UTF_8).length).get.bytes,
    value.getBytes(StandardCharsets.UTF_8)), CLTypeInfo(CLType.String), value)
  ).toOption

  //ByteArray
  def ByteArray(bytes: Array[Byte]): Option[CLValue] = Try(new CLValue(bytes, CLByteArrayTypeInfo(bytes.length), HexUtils.toHex(bytes).get)).toOption


  //PublicKey
  def PublicKey(value: CLPublicKey): Option[CLValue] = Try(new CLValue(value.formatAsByteAccount, CLTypeInfo(CLType.PublicKey), value.formatAsHexAccount.get)).toOption

  def PublicKey(value: String): Option[CLValue] = PublicKey(CLPublicKey(value).get) //get is called inside Try


  //URef
  def URef(value: URef): Option[CLValue] = Try {
    val bytes = new Array[Byte](33)
    val urefBytes = HexUtils.fromHex(value.format).getOrElse(Array.empty[Byte])
    Array.copy(urefBytes, 0, bytes, 1, urefBytes.length)
    bytes(32) = value.accessRights.bits
    new CLValue(bytes, CLTypeInfo(CLType.URef), value.format)
  }.toOption

  //URef
  def URef(value: String): Option[CLValue] = URef(com.casper.sdk.types.cltypes.URef(value).get)
  //else
  // None


  //Key
  def Key(value: CLKeyValue): Option[CLValue] = Try(new CLValue(value.getBytes, new CLKeyInfo(value.keyType), value.parsed)).toOption


  //Key
  def Key(value: String): Option[CLValue] = Key(CLKeyValue(value).get)

  //Option
  def Option(value: Option[CLValue]): Option[CLValue] = Try {
    val bytes = new Array[Byte](1 + value.get.bytes.length)
    //Some tag
    bytes(0) = 0x01
    Array.copy(value.get.bytes, 0, bytes, 1, value.get.bytes.length)
    new CLValue(bytes, new CLOptionTypeInfo(value.get.cl_infoType), value.get.parsed)
  }.toOption

  //None
  def OptionNone(value: CLTypeInfo): Option[CLValue] = Try {
    val bytes = new Array[Byte](1)
    //None tag
    bytes(0) = 0x00
    new CLValue(bytes, new CLOptionTypeInfo(value), Json.Null)
  }.toOption

  //List
  def List(values: CLValue*): Option[CLValue] = Try {
    import util.control.Breaks._
    val builder = new ArrayBuilder.ofByte
    var parsed = Array.empty[Any]
    val elementCountBytes = U32(values.size).get.bytes
    builder.addAll(elementCountBytes)
    val cl_typeInfo0 = values(0).cl_infoType
    var isListOfSameElements = true

    for (value <- values) {
      breakable {
        if (!value.cl_infoType.equals(cl_typeInfo0)) {
          isListOfSameElements = false
          break
        } else {
          builder.addAll(value.bytes)
          parsed = parsed.:+(value.parsed)
        }
      }
    }
    if (isListOfSameElements)
      new CLValue(builder.result(), if (values.isEmpty) new CLTypeInfo(CLType.Unit) else new CLListTypeInfo(values(0).cl_infoType), parsed)
    else null
  }.toOption
  // Result Ok

  def Ok(value: CLValue, err: CLTypeInfo): Option[CLValue] = Try {
    val bytes = new Array[Byte](1 + value.bytes.length)
    //Ok tag
    bytes(0) = 0x01
    Array.copy(value.bytes, 0, bytes, 1, value.bytes.length)
    new CLValue(bytes, new CLResultTypeInfo(value.cl_infoType, err), value.parsed)
  }.toOption

  // Result Err
  def Err(value: CLValue, ok: CLTypeInfo): Option[CLValue] = Try {
    val bytes = new Array[Byte](1 + value.bytes.length)
    //Err tag
    bytes(0) = 0x00
    Array.copy(value.bytes, 0, bytes, 1, value.bytes.length)
    new CLValue(bytes, new CLResultTypeInfo(ok, value.cl_infoType), value.parsed)
  }.toOption

  // Tuple1
  def Tuple1(value: CLValue): Option[CLValue] = Try {
    var parsed = Array.empty[Any]
    parsed = parsed.:+(value.parsed)
    new CLValue(value.bytes, new CLTuple1TypeInfo(value.cl_infoType), parsed)
  }.toOption

  // Tuple2
  def Tuple2(value1: CLValue, value2: CLValue): Option[CLValue] = Try {
    val builder = new ArrayBuilder.ofByte
    builder.addAll(value1.bytes).addAll(value2.bytes)
    var parsed = Array.empty[Any]
    parsed = parsed.:+(value1.parsed).:+(value2.parsed)
    new CLValue(builder.result(), new CLTuple2TypeInfo(value1.cl_infoType, value2.cl_infoType), parsed)
  }.toOption

  // Tuple3
  def Tuple3(value1: CLValue, value2: CLValue, value3: CLValue): Option[CLValue] = Try {
    require(value1 != null && value2 != null && value3 != null)
    val builder = new ArrayBuilder.ofByte
    builder.addAll(value1.bytes).addAll(value2.bytes).addAll(value3.bytes)
    var parsed = Array.empty[Any]
    parsed = parsed.:+(value1.parsed).:+(value2.parsed).:+(value3.parsed)
    new CLValue(builder.result(), new CLTuple3TypeInfo(value1.cl_infoType, value2.cl_infoType, value3.cl_infoType), parsed)
  }.toOption
}