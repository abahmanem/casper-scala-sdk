package com.casper.sdk.util

import org.apache.commons.codec.DecoderException
import org.apache.commons.codec.binary.Hex

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
  def toHex(bytes: Array[Byte]): Option[String] = try {
    Some(Hex.encodeHexString(bytes))
  } catch {
    case e: Exception => None
  }

  /**
   * convert hex string into byte array
   *
   * @param hex : hex string
   * @return byte array
   */
  def fromHex(hex: String): Array[Byte] = try {
    Hex.decodeHex(hex.toCharArray)
  }
  catch {
    case x: DecoderException => throw new IllegalArgumentException("Unable to decode: " + hex, x)
  }
}