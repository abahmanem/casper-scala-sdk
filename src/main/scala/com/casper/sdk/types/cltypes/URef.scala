package com.casper.sdk.types.cltypes

import com.casper.sdk.json.deserialize.URefDeserializer
import com.casper.sdk.util.HexUtils
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import scala.util.{Try, Success, Failure}
/**
 * Unforgeatable Reference
 *
 * @param bytes
 */
@JsonDeserialize(`using` = classOf[URefDeserializer])
class URef(
            val bytes: Array[Byte],
            val accessRights: AccessRight
          ) extends Tag {

  /**
   * format Uref objet into : uref-51215724cc359a60797f64d88543002a069176f3ea92d4c37d31304e2849ef13-004
   *
   * @return
   */
  def format: String = String.format(URef.UREF_PREFIX + "-%s-%03d", HexUtils.toHex(bytes).get, accessRights.bits)
  override def tag = 2
}

/**
 * Companion object
 */
object URef {
  val UREF_PREFIX = "uref"

  /**
   *
   * @param uref
   * @return
   */
  def apply(uref: String): Option[URef] =  {
    Try {
     Option(new URef(URef.parseUref(uref).get, URef.getAccessRight(uref)))
    } match {
      case Success(x) => x
      case Failure(err) =>        None
    }




  }

  /**
   * extract AccessRight from Uref String
   *
   * @param uref
   * @return
   */
  private def getAccessRight(uref: String): AccessRight = {
    AccessRight.values.find(_.bits == Integer.parseInt(uref.charAt(uref.length - 1).toString)) match {
      case None => AccessRight.ACCESS_NONE
      case Some(a) => a
    }
  }

  /**
   * parse a Uref String into A byte array
   *
   * @param uref
   * @return
   */
  private def parseUref(uref: String): Option[Array[Byte]] = {
    require(uref!=null)
    Try {

      val opt = uref.split("-")
      opt(0) match {
        case UREF_PREFIX => Option(HexUtils.fromHex(opt(1)).get)
        case _ => None
      }

    } match {
      case Success(x) => x
      case Failure(err) => {

        None

      }
    }







/*
    opt(0) match {
      case UREF_PREFIX => Option(HexUtils.fromHex(opt(1)).get)
      case _ => None //throw new IllegalArgumentException(uref + " is not a valid uref")
    }
 */
  }

}



