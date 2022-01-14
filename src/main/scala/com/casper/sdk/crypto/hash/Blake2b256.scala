package com.casper.sdk.crypto.hash
import com.casper.sdk.types.cltypes.CLPublicKey
import org.bouncycastle.crypto.digests.Blake2bDigest

object Blake2b256 extends App{

  /**
   * gives a 32 byte hashed array from a byte array
   *
   * @param bytes
   * @return hex string  32 bytes array
   */
   def hash(input: Array[Byte]): Array[Byte] = {
    val digest = new Blake2bDigest(256)
    synchronized {
      digest.update(input, 0, input.length)
      val res = new Array[Byte](32)
      digest.doFinal(res, 0)
      res
    }
  }
}
