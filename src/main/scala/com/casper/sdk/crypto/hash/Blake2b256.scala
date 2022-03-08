package com.casper.sdk.crypto.hash

import com.casper.sdk.types.cltypes.CLPublicKey
import org.bouncycastle.crypto.digests.Blake2bDigest

import java.nio.charset.StandardCharsets

/**
 * Blake2b256 Hash object utility
 */
object Blake2b256 {
  /**
   * gives a byte hashed array from a byte array
   *
   * @param input : Array[Byte]
   * @return  hash array   array
   */
  def hash(input: Array[Byte]): Array[Byte] = {
    require(input != null)
    val digest = new Blake2bDigest(256)
    synchronized {
      digest.update(input, 0, input.length)
      val res = new Array[Byte](digest.getDigestSize)
      digest.doFinal(res, 0)
      res
    }
  }

  /**
   * get accountHash from CL cLPublicKey
   *
   * @param cLPublicKey : CLPublicKey
   * @return Array[Byte]
   */

  def CLPublicKeyToAccountHash(cLPublicKey: CLPublicKey): Array[Byte] = {
    require(cLPublicKey != null)
    val digest = new Blake2bDigest(256)
    val algorithm = cLPublicKey.keyAlgorithm.toString.toLowerCase
    digest.update(algorithm.getBytes(StandardCharsets.UTF_8), 0, algorithm.length)
    digest.update(0x00)
    digest.update(cLPublicKey.bytes, 0, cLPublicKey.bytes.length)
    new Array[Byte](digest.getDigestSize)
  }
}
