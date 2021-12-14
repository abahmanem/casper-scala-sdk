package com.casper.sdk.util

import org.apache.commons.codec.DecoderException
import org.apache.commons.codec.binary.Hex

object ByteUtils {
  /**
   * convert a hexString into a byte array
   *
   * @param hexString
   * @return
   */
  def hexToBytes(hexString: String): Array[Byte] = try {
    Hex.decodeHex(hexString)
  }
  catch {
    case x: DecoderException => throw new IllegalArgumentException("Unable to decode: " + hexString, x)
  }
  //}

  /**
   * convert a bytes array into hex string
   *
   * @param bytes
   * @param sep
   * @return
   */
  def bytesToHex(bytes: Array[Byte]): String = {
    Hex.encodeHexString(bytes)
  }

  def hextoT[T](hex: String, f: BigInt => T) = f(BigInt(hex.drop(2), 16))

  def hex2Bytes(hex: String): Array[Byte] = hextoT(hex, _.toByteArray)


  def join(parts : Array[Byte]*) : Array[Byte] ={
   //Array.concat(parts)
    null
  }


}
