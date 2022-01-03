package com.casper.sdk.types.cltypes

import com.casper.sdk.json.deserialize.CLPublicKeyDeserializer
import com.casper.sdk.types.cltypes
import com.casper.sdk.types.cltypes.CLPublicKey.dropAlgorithmBytes
import com.casper.sdk.util.{ByteUtils, HexUtils}
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.casper.sdk.types.cltypes.KeyAlgorithm
/**
 * CLPublicKey : Casper system public key
 *
 * @param bytes
 */
@JsonDeserialize(`using` = classOf[CLPublicKeyDeserializer])
class CLPublicKey(
                   override val bytes: Array[Byte]
                 ) extends CLValue(bytes, CLType.PublicKey) with Tag {

  var keyAlgorithm: KeyAlgorithm = null

  /**
   * Constructor using a key bytes array and an Algorithm : 06cA7c39cD272DbF21a86EeB3B36B7c26E2e9b94af64292419f7862936bcA2cA
   *
   * @param key
   * @param algo
   */
  def this(bytes: Array[Byte], keyAlgo: KeyAlgorithm) = {
    this(bytes)
    keyAlgorithm = keyAlgo
    assert(bytes!=null)
    assert(keyAlgo!=null)
  }

  /**
   * Constructor using a hex account String : 0106cA7c39cD272DbF21a86EeB3B36B7c26E2e9b94af64292419f7862936bcA2cA
   * @param hexKey
   */


  def this(hexKey: String) = this(dropAlgorithmBytes(HexUtils.fromHex(hexKey)),KeyAlgorithm.fromId(hexKey.charAt(1)))

  /**
   * format to Hex account , ie : 0106cA7c39cD272DbF21a86EeB3B36B7c26E2e9b94af64292419f7862936bcA2cA, 01 being tag bytes
   * @return
   */
  def formatAsHexAccount : String = HexUtils.toHex(formatAsByteAccount)
  

  /**
   * format to Byte array with algorithm
   * @return
   */
  def formatAsByteAccount : Array[Byte] = ByteUtils.join(Array.fill(1)(keyAlgorithm.bits.toByte), bytes)

  override  def tag=1
}

/**
 * Companion object
 */
object CLPublicKey {
  /**
   * remove algorithm tag bytes
   * @param key
   * @return
   */
  def dropAlgorithmBytes(key: Array[Byte]): Array[Byte] = {
    key.drop(1)
  }

}