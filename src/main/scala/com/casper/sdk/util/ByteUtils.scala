package com.casper.sdk.util

import math.BigInt.int2bigInt

/**
 * Bytes utility object
 */
object ByteUtils {
  /**
   * concat byte arrays
   *
   * @param parts
   * @return
   */
  def join(parts: Array[Byte]*): Array[Byte] = {
    require(parts != null)
    parts.toArray.flatten
  }

  /**
   *
   * Serialize U128,U256 and U512 casper types
   *
   * @param value
   * @param maxBytes
   * @return Array[Byte]
   */
  def serializeArbitraryWidthNumber(value: BigInt, maxBytes: Int): Array[Byte] = {
    require(value != null && value.toByteArray.length <= maxBytes)
    var bytes = value.toByteArray
    //remove leading zeros
    if (bytes.length > 1 && bytes(0) == 0) {
      val dest :  Array[Byte] =  new Array[Byte](bytes.length-1)
      Array.copy(bytes, 1, dest, 0, bytes.length - 1)
      bytes = dest
    }
    //Little-Endian byte order
    bytes = bytes.reverse
    ByteUtils.join(bytes.length.toByteArray, bytes)
  }

  /**
   * Serialize U32,I32,I62 and U64 casper types
   *
   * @param value
   * @param maxBytes
   * @return Array[Byte]
   */

  def serializeFixedWidthNumber(value: BigInt, maxBytes: Int): Array[Byte] = {
    require(value != null && value.toByteArray.length <= maxBytes)
    val bytes = value.toByteArray
    var res = new Array[Byte](maxBytes)
    if (bytes.length < maxBytes) {
      res = Array.tabulate(maxBytes) { i =>
        // add 0x00 for positive and -0xff for negative numbers
        if (i < (maxBytes - bytes.length)) (if (value.signum == -1) 0xff else 0).toByte
        else bytes(i - (maxBytes - bytes.length))
      }
    }
    // drop leading 0 if bytes length = maxBytes + 1
    else if (bytes.length == maxBytes + 1 && bytes(0) == 0) Array.copy(bytes, 1, res, 0, maxBytes)
    //Little-Endian byte order
    res.reverse
  }
}