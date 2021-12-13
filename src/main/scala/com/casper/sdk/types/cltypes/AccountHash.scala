package com.casper.sdk.types.cltypes

import com.casper.sdk.json.deserialize.AccountHashDeserializer
import com.casper.sdk.util.HexUtils
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

/**
 * AccountHash enttiy class
 *
 * @param bytes
 */
@JsonDeserialize(`using` = classOf[AccountHashDeserializer])
case class AccountHash(override val bytes: Array[Byte]) extends CLValue(bytes, CLType.Default) {

  /**
   * format AccountHash objet into : account-hash-85148dcd6c54b77e462a9acf387fb05aca953a83011db2c601716de0af1cf47c
   *
   * @return
   */
  def format: String = String.format(AccountHash.ACCOUNT_PREFIX + "%s", HexUtils.bytesToHex(bytes))

  /**
   * Constructor using s String Uref value
   *
   * @param uref
   */
  def this(account: String) = this(AccountHash.parseAccount(account))
}

/**
 * Companion object
 */
object AccountHash {
  val ACCOUNT_PREFIX = "account-hash-"
  def parseAccount(account: String): Array[Byte] = {
    val prefix = account.substring(0, 13)
    prefix match {
      case ACCOUNT_PREFIX => HexUtils.hex2Bytes(account.replace(prefix, ""))
      case _ => throw new IllegalArgumentException(account + " is not a valid account")
    }
  }
}

