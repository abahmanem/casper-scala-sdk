package com.casper.sdk.types.cltypes

import com.casper.sdk.json.deserialize.CLValueDeSerializer
import com.casper.sdk.json.serialize.CLValueSerializer
import com.casper.sdk.util.{ByteUtils, HexUtils}
import com.fasterxml.jackson.databind.annotation.{JsonDeserialize, JsonSerialize}

import java.nio.charset.StandardCharsets
import scala.collection.mutable.ArrayBuilder
import scala.math.BigInt.int2bigInt


/**
 * CLValue : datas used by smart contracts.
 *
 * @param bytes
 * @param cl_type
 * @param parsed
 */
@JsonSerialize(`using` = classOf[CLValueSerializer])
@JsonDeserialize(`using` = classOf[CLValueDeSerializer])
class CLValue(
               val bytes: Array[Byte],
               val cl_infoType: CLTypeInfo,
               var parsed: Any
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
  def this(hexBytes: String, clType: CLTypeInfo, parsed: Any) = this(HexUtils.fromHex(hexBytes), clType, parsed)

}

//companion object
object CLValue {

  //Bool
  def Bool(value: Boolean): CLValue = {
    val bytes = Array[Byte]((if (value) 0x01 else 0x00).toByte)
    new CLValue(bytes, CLTypeInfo(CLType.Bool), value);
  }

  //Unit
  def Unit(): CLValue = {
    new CLValue(Array.empty[Byte], CLTypeInfo(CLType.Unit), null);
  }

  //U8
  def U8(value: Byte): CLValue = {
    new CLValue(new Array[Byte](value), CLTypeInfo(CLType.U8), value);
  }

  //I32
  def I32(value: BigInt): CLValue = {
    assert(value != null)
    new CLValue(ByteUtils.serializeFixedWidthNumber(value, 4), CLTypeInfo(CLType.I32), value)
  }

  //U32
  def U32(value: BigInt): CLValue = {
    assert(value != null)
    new CLValue(ByteUtils.serializeFixedWidthNumber(value, 4), CLTypeInfo(CLType.U32), value)
  }

  //U64
  def U64(value: BigInt): CLValue = {
    assert(value != null)
    new CLValue(ByteUtils.serializeFixedWidthNumber(value, 8), CLTypeInfo(CLType.U64), value)
  }


  //I64
  def I64(value: BigInt): CLValue = {
    assert(value != null)
    new CLValue(ByteUtils.serializeFixedWidthNumber(value, 8), CLTypeInfo(CLType.I64), value)
  }

  //U128
  def U128(value: BigInt): CLValue = {
    assert(value != null)
    new CLValue(ByteUtils.serializeArbitraryWidthNumber(value, 16), CLTypeInfo(CLType.U128), value)
  }

  //U128
  def U256(value: BigInt): CLValue = {
    assert(value != null)
    new CLValue(ByteUtils.serializeArbitraryWidthNumber(value, 32), CLTypeInfo(CLType.U256), value)
  }

  //U512
  def U512(value: BigInt): CLValue = {
    assert(value != null)
    new CLValue(ByteUtils.serializeArbitraryWidthNumber(value, 64), CLTypeInfo(CLType.U512), value)
  }

  //String
  def String(value: String): CLValue = {
    assert(value != null)
    new CLValue(ByteUtils.join(U32(value.getBytes(StandardCharsets.UTF_8).length).bytes,
      value.getBytes(StandardCharsets.UTF_8)), CLTypeInfo(CLType.String), value)
  }

  //ByteArray
  def ByteArray(bytes: Array[Byte]): CLValue = {
    assert(bytes != null)
    new CLValue(bytes, CLByteArrayTypeInfo(bytes.length), HexUtils.toHex(bytes))
  }


  //PublicKey
  def PublicKey(value: CLPublicKey ): CLValue = {
    assert(value != null)
    new CLValue(value.formatAsByteAccount , CLTypeInfo(CLType.PublicKey), value.formatAsHexAccount)
  }

  def PublicKey(value: String ): CLValue = {
    assert(value != null)
    PublicKey( CLPublicKey(value) )
  }


  //URef
  def URef(value: URef): CLValue = {
    assert(value != null)
    val bytes = new Array[Byte](33)
    Array.copy(value.bytes, 0, bytes, 1, value.bytes.length)
    bytes(32)=value.accessRights.bits
    new CLValue(bytes, CLTypeInfo(CLType.URef), value.format)
  }

  //URef
  def URef(value: String): CLValue = {
    assert(value != null)
    URef(com.casper.sdk.types.cltypes.URef(value))
  }



  //Key
  def Key(value: CLKeyValue): CLValue = {
    assert(value != null)
    new CLValue(value.getBytes, new CLKeyInfo(value.keyType), value.parsed)
  }


  //Key
  def Key(value: String): CLValue = {
    assert(value != null)
    Key(CLKeyValue(value))
  }


  //Option
  def Option(value: CLValue): CLValue = {
    assert(value != null)
    val bytes = new Array[Byte](1 + value.bytes.length)
    //Some tag
    bytes(0) = 0x01
    Array.copy(value.bytes, 0, bytes, 1, value.bytes.length)
    new CLValue(bytes, new CLOptionTypeInfo(value.cl_infoType), value.parsed)
  }

  //None
  def OptionNone(value: CLTypeInfo): CLValue = {
    assert(value != null)
    val bytes = new Array[Byte](1)
    //None tag
    bytes(0) = 0x00
    new CLValue(bytes, new CLOptionTypeInfo(value), null)
  }

  //List
  def List(values: CLValue*): CLValue = {
    assert(values != null)
    val builder = new ArrayBuilder.ofByte
    var parsed = Array.empty[Any]
    val elementCountBytes = U32(values.size).bytes
    builder.addAll(elementCountBytes)
    for (value <- values)
      {builder.addAll(value.bytes)
        parsed = parsed.:+(value.parsed)
         }
     new CLValue(builder.result(), if (values.isEmpty) new CLTypeInfo(CLType.Unit) else new CLListTypeInfo(values(0).cl_infoType), parsed)
  }
  // Result Ok

  def Ok(value: CLValue, err: CLTypeInfo): CLValue = {
    assert(value != null)
    val bytes = new Array[Byte](1 + value.bytes.length)
    //Ok tag
    bytes(0) = 0x01
    Array.copy(value.bytes, 0, bytes, 1, value.bytes.length)
    new CLValue(bytes, new CLResultTypeInfo(value.cl_infoType, err), null)
  }

  // Result Err
  def Err(value: CLValue, ok: CLTypeInfo): CLValue = {
    assert(value != null)
    val bytes = new Array[Byte](1 + value.bytes.length)
    //Err tag
    bytes(0) = 0x00
    Array.copy(value.bytes, 0, bytes, 1, value.bytes.length)
    new CLValue(bytes, new CLResultTypeInfo(ok, value.cl_infoType), null)
  }

  // Tuple1
  def Tuple1(value: CLValue): CLValue = {
    assert(value != null)
    new CLValue(value.bytes, new CLTuple1TypeInfo(value.cl_infoType), value.parsed)
  }

  // Tuple2
  def Tuple2(value1: CLValue, value2: CLValue): CLValue = {
    assert(value1 != null && value2 != null)
    val builder = new ArrayBuilder.ofByte
    builder.addAll(value1.bytes).addAll(value2.bytes)
    new CLValue(builder.result(), new CLTuple2TypeInfo(value1.cl_infoType, value2.cl_infoType), HexUtils.toHex(builder.result()))
  }

  // Tuple3
  def Tuple3(value1: CLValue, value2: CLValue, value3: CLValue): CLValue = {
    assert(value1 != null && value2 != null && value3 != null)
    val builder = new ArrayBuilder.ofByte
    builder.addAll(value1.bytes).addAll(value2.bytes).addAll(value3.bytes)
    new CLValue(builder.result(), new CLTuple3TypeInfo(value1.cl_infoType, value2.cl_infoType, value3.cl_infoType), HexUtils.toHex(builder.result()))
  }

  //Map
/*
  def Map(key: CLValue, value: CLValue): CLValue = {
    null
  }

 */
}