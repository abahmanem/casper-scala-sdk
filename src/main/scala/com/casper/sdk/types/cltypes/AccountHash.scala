package com.casper.sdk.types.cltypes

import com.casper.sdk.json.deserialize.AccountHashDeserializer
import com.casper.sdk.util.HexUtils
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import scala.util.{Failure, Success, Try}
/**
 * AccountHash entitiy class
 *
 * @param bytes
 */
@JsonDeserialize(`using` = classOf[AccountHashDeserializer])
case class AccountHash(
                   val bytes: Array[Byte]
                 ){

  /**
   * format AccountHash objet into : account-hash-85148dcd6c54b77e462a9acf387fb05aca953a83011db2c601716de0af1cf47c
   *
   * @return
   */
  def format: String = String.format(AccountHash.ACCOUNT_PREFIX + "%s", HexUtils.toHex(bytes).get)
}

/**
 * Companion object
 */
object AccountHash {
  val ACCOUNT_PREFIX = "account-hash-"

  /**
   *
   * @param account
   * @return
   */
  def apply(account: String) : Option[AccountHash] = Try {
    if(parseAccount(account)==null)throw IllegalArgumentException("not a valid account")
    new AccountHash(parseAccount(account))
    } match {
      case Success(x) => Some(x)
      case Failure(err) => None
      }

  /**
   * pare an account String
   * @param account
   * @return
   */

  def parseAccount(account: String): Array[Byte] = {
    require(account!=null)
    Try {
      val prefix = account.substring(0, 13)
      prefix match {
        case ACCOUNT_PREFIX => HexUtils.fromHex(account.replace(prefix, "")).get
        case _ => throw IllegalArgumentException("not a valid account")
      }
    } match {
      case Success(x) => x
      case Failure(err) => null
    }
  }
}


