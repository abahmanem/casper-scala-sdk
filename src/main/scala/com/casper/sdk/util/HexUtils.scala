package com.casper.sdk.util

import org.apache.commons.codec.DecoderException
import org.apache.commons.codec.binary.Hex
import scala.language.postfixOps

object HexUtils {

  /**
   * Convert byte array to hex string
   *
   * @param bytes
   * @param sep
   * @return
   */
  def toHex(bytes: Array[Byte], separator: Option[String] = None): String = bytes.map("%02x".format(_)).mkString(separator.getOrElse(""))

  /**
   *
   * @param hex
   * @param f
   * @tparam T
   * @return
   */
  def hextoT[T](hex: String, f: BigInt => T) = f(BigInt(hex, 16))

  /**
   * convert hex string into byte array
   *
   * @param hex : hex string
   * @return byte array
   */
  def fromHex(hex: String): Array[Byte] = try {
    require((hex.length & 1) == 0, "Hex string must have length of 2n.")
    hextoT(hex, _.toByteArray)
  }
  catch {
    case x: NumberFormatException => throw new IllegalArgumentException("Unable to decode: " + hex, x)
  }

}