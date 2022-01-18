package com.casper.sdk.types.cltypes

import com.casper.sdk.json.deserialize.CLTypeDeserialiser
import com.casper.sdk.json.serialize.CLTypeInfoSerializer
import com.casper.sdk.types.cltypes
import com.fasterxml.jackson.databind.annotation.{JsonDeserialize, JsonSerialize}

/**
 * Enum of CLType with data bytes array
 */

@JsonDeserialize(`using` = classOf[CLTypeDeserialiser])
enum CLType(val clType: Int) {

  case Bool extends CLType(0)

  /** signed 32-bit integer primitive */
  case I32 extends CLType(1)

  /** signed 64-bit integer primitive */
  case I64 extends CLType(2)

  /** unsigned 8-bit integer primitive */
  case U8 extends CLType(3)

  /** unsigned 32-bit integer primitive */
  case U32 extends CLType(4)

  /** unsigned 64-bit integer primitive */
  case U64 extends CLType(5)

  /** unsigned 128-bit integer primitive */
  case U128 extends CLType(6)

  /** unsigned 256-bit integer primitive */
  case U256 extends CLType(7)

  /** unsigned 512-bit integer primitive */
  case U512 extends CLType(8)

  /** singleton value without additional semantics */
  case Unit extends CLType(9)

  /** e.g. "Hello, World!" */
  case String extends CLType(10)

  /** global state key */
  case Key extends CLType(11)

  /** unforgeable reference */
  case URef extends CLType(12)

  /** optional value of the given type Option(CLType) */
  case Option extends CLType(13)

  /** List of values of the given type (e.g. Vec in rust). List(CLType) */
  case List extends CLType(14)

  /** Byte array prefixed with U32 length (FixedList) */
  case ByteArray extends CLType(15)

  /** co-product of the the given types; one variant meaning success, the other failure */
  case Result extends CLType(16)

  /** Map(CLType, CLType), // key-value association where keys and values have the given types */
  case Map extends CLType(17)

  /** Tuple1(CLType) single value of the given type */
  case Tuple1 extends CLType(18)

  /** Tuple2(CLType, CLType), // pair consisting of elements of the given types */
  case Tuple2 extends CLType(19)

  /** Tuple3(CLType, CLType, CLType), // triple consisting of elements of the given types */
  case Tuple3 extends CLType(20)

  /** Indicates the type is not known */
  case Any extends CLType(21)

  /** Public key */
  case PublicKey extends CLType(22)
}
object CLType{
  /**
   * checks if CLType is numeric
   * @param cltype
   * @return
   */
  def isNumeric(cltype : CLType) : Boolean = {
    cltype match {
      case I32 | I64 | U8 | U32 | U64 | U128 | U512 | U256 => true
      case _ => false
    }
  }
}
