package com.casper.sdk.util

import com.rfksystems.blake2b.Blake2b
import com.rfksystems.blake2b.security.Blake2bProvider

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.Security
import java.security.Security

/**
 * Blake2b Hash utility object
 */
object HashUtil {
  Security.addProvider(new Blake2bProvider())

  /**
   * gives a 32 byte hashed array from a byte array
   *
   * @param bytes
   * @return hex string  32 bytes array
   */
  def hash(bytes: Array[Byte]): Array[Byte] = try {
    val blake2bDigest = MessageDigest.getInstance(Blake2b.BLAKE2_B_256)
    blake2bDigest.update(bytes)
    blake2bDigest.digest()
  } catch {
    case e: NoSuchAlgorithmException => throw new RuntimeException("an error occured", e)
  }
}
