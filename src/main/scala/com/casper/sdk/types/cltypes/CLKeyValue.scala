package com.casper.sdk.types.cltypes


import com.casper.sdk.types.cltypes._
import com.casper.sdk.util.{ByteUtils, HexUtils, JsonConverter}
import com.fasterxml.jackson.databind.ObjectMapper

/**
 * A key in global state
 */
class CLKeyValue(override val bytes: Array[Byte],
                 val keyType: KeyType,
                 override val parsed: Any)
  extends CLValue(bytes, new CLKeyInfo(keyType), parsed) {

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

object CLKeyValue {

  /**
   * Get instance from a bytes array
   *
   * @param hexBytes
   * @param keyType
   * @return CLKeyValue
   */
  def apply(hexBytes: Array[Byte]): Option[CLKeyValue] = {
    assert(hexBytes != null)
    hexBytes(0) match {
      case 0x00 => CLKeyValue(HexUtils.toHex(hexBytes.drop(0)).get, KeyType.Account)
      case 0x01 => CLKeyValue(HexUtils.toHex(hexBytes.drop(0)).get, KeyType.Hash)
      case 0x02 => CLKeyValue(HexUtils.toHex(hexBytes.drop(0)).get, KeyType.Uref)
      case 0x03 => CLKeyValue(HexUtils.toHex(hexBytes.drop(0)).get, KeyType.Transfer)
      case 0x04 => CLKeyValue(HexUtils.toHex(hexBytes.drop(0)).get, KeyType.DeployInfo)
      case 0x05 => CLKeyValue(HexUtils.toHex(hexBytes.drop(0)).get, KeyType.EraInfo)
      case 0x06 => CLKeyValue(HexUtils.toHex(hexBytes.drop(0)).get, KeyType.Balance)
      case 0x07 => CLKeyValue(HexUtils.toHex(hexBytes.drop(0)).get, KeyType.Bid)
      case 0x08 => CLKeyValue(HexUtils.toHex(hexBytes.drop(0)).get, KeyType.Withdraw)
      case _ => throw IllegalArgumentException("Invalid Key " + hexBytes(0))
    }
  }

  /**
   * Get instance from hexString and Key Type
   *
   * @param hexBytes Hex String
   * @param keyType  KeyType
   * @return CLKeyValue
   */
  def apply(hexBytes: String, keyType: KeyType): Option[CLKeyValue] = CLKeyValue(keyType.prefix + "-" + hexBytes)

  /**
   * Get instance from prefixed hexString : eg : transfer-e330a31701205e3871cb4f7e14d3ff26074735c84b0e54b7a75f553a8405d182
   *
   * @param key
   * @return CLKeyValue
   */
  def apply(key: String): Option[CLKeyValue] = {
    require(key != null)
    try {
      Option(new CLKeyValue(HexUtils.fromHex(key.substring(key.lastIndexOf("-") + 1)).get, KeyType.getByPrefix(key.substring(0, key.lastIndexOf("-"))).get, parsedValue(key)))
    } catch {
      case _: Exception => None
    }
  }

  /**
   * compute parsed value from string key
   *
   * @param key
   * @return Any
   */
  def parsedValue(key: String): Any = {
    require(key != null)
    try {
      val keyType = KeyType.getByPrefix(key.substring(0, key.lastIndexOf("-"))).get
      val json = new StringBuilder("")
      json.append("{").append("\"").append(keyType).append("\"").append(":").append("\"").append(key).append("\"").append("}")
      val parsed = new ObjectMapper().readTree(json.toString())
      parsed
    }
    catch {
      case _: Exception => null
    }
  }
}