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
   * Get instance from a bytes array
   * @param hexBytes
   * @param keyType
   * @return CLKeyValue
   */
  def apply (hexBytes:Array[Byte]) : CLKeyValue ={

    hexBytes(0) match {
      case 0x00 =>  CLKeyValue(HexUtils.toHex(hexBytes.drop(0)),KeyType.Account)
      case 0x01 =>  CLKeyValue(HexUtils.toHex(hexBytes.drop(0)),KeyType.Hash)
      case 0x02 =>  CLKeyValue(HexUtils.toHex(hexBytes.drop(0)),KeyType.Uref)
      case 0x03 =>  CLKeyValue(HexUtils.toHex(hexBytes.drop(0)),KeyType.Transfer)
      case 0x04 =>  CLKeyValue(HexUtils.toHex(hexBytes.drop(0)),KeyType.DeployInfo)
      case 0x05 =>  CLKeyValue(HexUtils.toHex(hexBytes.drop(0)),KeyType.EraInfo)
      case 0x06 =>  CLKeyValue(HexUtils.toHex(hexBytes.drop(0)),KeyType.Balance)
      case 0x07 =>  CLKeyValue(HexUtils.toHex(hexBytes.drop(0)),KeyType.Bid)
      case 0x08 =>  CLKeyValue(HexUtils.toHex(hexBytes.drop(0)),KeyType.Withdraw)
      case _ => throw IllegalArgumentException("Invalid Key "+ hexBytes(0))
    }
  }

  /**
   * Get instance from hexString and KEy Type
   * @param hexBytes Hex String
   * @param keyType  KeyType
   * @return CLKeyValue
   */
  def apply (hexBytes:String,keyType: KeyType) : CLKeyValue =  CLKeyValue(keyType.prefix+"-"+hexBytes)

  /**
   * Get instance from string : eg : transfer-e330a31701205e3871cb4f7e14d3ff26074735c84b0e54b7a75f553a8405d182
   * @param key
   * @return CLKeyValue
   */
   def apply (key:String) : CLKeyValue = new CLKeyValue(HexUtils.fromHex(key.substring(key.lastIndexOf("-")+1)),KeyType.getByPrefix(key.substring(0,key.lastIndexOf("-"))),parsedValue(key))
    /**
   * compute parsed value from string key
   * @param key
   * @return  Any
   */
  def parsedValue(key:String) : Any ={
    val keyType = KeyType.getByPrefix(key.substring(0,key.lastIndexOf("-")))
    val bytes = HexUtils.fromHex(key.substring(key.lastIndexOf("-")+1))
    val mapper = new ObjectMapper
    val json = new StringBuilder("")
    json.append("{").append("\"").append(keyType).append("\"").append(":").append("\"").append(key).append("\"").append("}")
    val parsed = mapper.readValue(json.toString(), classOf[Any])
    parsed
  }
}