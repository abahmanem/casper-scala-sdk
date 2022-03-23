package com.casper.sdk.util

import org.apache.commons.codec.DecoderException
import org.apache.commons.codec.binary.Hex
import scala.util.Try

/**
 * Hex utility object
 */
object HexUtils {

  /**
   * Convert byte array to hex string
   *
   * @param bytes
   * @param sep
   * @return
   */
  def toHex(bytes: Array[Byte]): Option[String] = Try(Hex.encodeHexString(bytes)).toOption

  /**
   * convert hex string into byte array
   *
   * @param hex : hex string
   * @return byte array
   */
  def fromHex(hex: String): Option[Array[Byte]] = Try(Hex.decodeHex(hex.toCharArray)).toOption
}
