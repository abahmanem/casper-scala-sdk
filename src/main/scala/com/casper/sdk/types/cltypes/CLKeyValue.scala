package com.casper.sdk.types.cltypes


import com.casper.sdk.types.cltypes._
import com.casper.sdk.util.{ByteUtils, HexUtils}
import com.fasterxml.jackson.databind.ObjectMapper

/**
 * A key in global state
 */
class CLKeyValue(override val bytes: Array[Byte],
                 val keyType: KeyType,
                 override val parsed: Any)
  extends CLValue(bytes, new CLKeyInfo(keyType), parsed) {

  /**
   * Constructor using a hex String, KeyType and a parsed value
   *
   * @param hexBytes
   * @param keyType
   * @param parsed
   */
  def this(hexBytes: String, keyType: KeyType, parsed: Any) = this(HexUtils.fromHex(hexBytes), keyType, parsed)

  /**
   * bytes with key tag bytes
   *
   * @return
   */
  def getBytes: Array[Byte] = ByteUtils.join(Array.fill(1)(keyType.tag.toByte), bytes)
}

/**
 * companion object
 */

object CLKeyValue{

  /**
   * Get instance from hexString and KEy Type
   * @param hexBytes
   * @param keyType
   * @return
   */
  def apply (hexBytes:String,keyType: KeyType) : CLKeyValue ={
    new CLKeyValue(hexBytes,keyType,parsedValue)
  }

  /**
   * Get instance from string : eg : transfer-e330a31701205e3871cb4f7e14d3ff26074735c84b0e54b7a75f553a8405d182
   * @param key
   * @return
   */
  //constructor from keys ie : transfer-e330a31701205e3871cb4f7e14d3ff26074735c84b0e54b7a75f553a8405d182
  def apply (key:String) : CLKeyValue ={
    val prefix = key.substring(0,key.lastIndexOf("-"))
    val keyType = KeyType.getByPrefix(prefix)
    val bytes = HexUtils.fromHex(key.substring(key.lastIndexOf("-")+1))
    new CLKeyValue(bytes,keyType,parsedValue(key))
  }

  def parsedValue(key:String) : Any ={
    val prefix = key.substring(0,key.lastIndexOf("-"))
    val keyType = KeyType.getByPrefix(prefix)
    val bytes = HexUtils.fromHex(key.substring(key.lastIndexOf("-")+1))
    val mapper = new ObjectMapper
    val json = new StringBuilder("")
    json.append("{").append("\"").append(keyType).append("\"").append(":").append("\"").append(key).append("\"").append("}")
    val parsed = mapper.readValue(json.toString(), classOf[Any])
    parsed
  }

}