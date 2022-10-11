package com.casper.sdk.types.cltypes


import com.casper.sdk.util.HexUtils
import scala.util.{Try, Success, Failure}

/**
 * Unforgeatable Reference
 *
 * @param bytes
 */
case class URef(
                 main_purse: String,
                 accessRights: AccessRight
               ) extends Tag {

  /**
   * format Uref objet into : uref-51215724cc359a60797f64d88543002a069176f3ea92d4c37d31304e2849ef13-004
   *
   * @return
   */
  def format: String = String.format(URef.UREF_PREFIX + "-%s-%03d", main_purse, accessRights.bits)

  override def tag = 2
}

/**
 * Companion object
 */
object URef {

  import io.circe.{Decoder, Encoder}

  implicit val decoder: Decoder[Option[URef]] = Decoder.decodeString.emapTry {
    str => Try(URef(str))
  }

  implicit val encoder: Encoder[URef] = (uref: URef) => Encoder.encodeString(uref.format)

  /**
   * prefix
   */
  val UREF_PREFIX = "uref"

  /**
   *
   * @param uref
   * @return
   */
  def apply(uref: String): Option[URef] = parseUref(uref) match {
    case Some(x) => Some(new URef(uref.split("-")(1), getAccessRight(uref)))
    case None => None
  }

  /**
   * extract AccessRight from Uref String
   *
   * @param uref
   * @return
   */
  def getAccessRight(uref: String): AccessRight = try {
    val opt = uref.split("-")
    AccessRight.values.find(_.bits == Integer.parseInt(opt(2).charAt(opt(2).length - 1).toString)) match {
      case None => AccessRight.ACCESS_NONE
      case Some(a) => a
    }
  } catch {
    case ex => AccessRight.ACCESS_NONE
  }

  /**
   * parse a Uref String into A byte array
   *
   * @param uref
   * @return
   */
  def parseUref(uref: String): Option[Array[Byte]] =
    try {
      val opt = uref.split("-")
      opt(0) match {
        case UREF_PREFIX => HexUtils.fromHex(opt(1))
        case _ => None
      }
    }
    catch {
      case ex => None
    }
}



