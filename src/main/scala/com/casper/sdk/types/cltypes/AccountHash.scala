package com.casper.sdk.types.cltypes


import com.casper.sdk.util.HexUtils
import scala.util.{Failure, Success, Try}

/**
 * AccountHash entitiy class
 *
 * @param bytes
 */
case class AccountHash(bytes: Array[Byte]) {

  /**
   * format AccountHash objet into : account-hash-85148dcd6c54b77e462a9acf387fb05aca953a83011db2c601716de0af1cf47c
   *
   * @return
   */
  def format: String = String.format(AccountHash.ACCOUNT_PREFIX + "%s", HexUtils.toHex(bytes).getOrElse(""))
}

/**
 * Companion object
 */
object AccountHash {

 import io.circe.{Decoder, Encoder}
 implicit val decoder: Decoder[Option[AccountHash]] = Decoder.decodeString.emapTry {
    str => Try(AccountHash(str))
  }
  implicit val encoder: Encoder[AccountHash] = (account: AccountHash) => Encoder.encodeString(account.format)

  val ACCOUNT_PREFIX = "account-hash-"

  /**
   *
   * @param account
   * @return
   */
  def apply(account: String): Option[AccountHash] = parseAccount(account) match {
    case Some(x) => Some(AccountHash(x))
    case None => None
  }


  /**
   * pare an account String
   *
   * @param account
   * @return
   */

  def parseAccount(account: String): Option[Array[Byte]] = try {

    val prefix = account.substring(0, 13)
    prefix match {
      case ACCOUNT_PREFIX => HexUtils.fromHex(account.replace(prefix, ""))
      case _ => None
    }
  }
  catch {
    case ex => None
  }
}


