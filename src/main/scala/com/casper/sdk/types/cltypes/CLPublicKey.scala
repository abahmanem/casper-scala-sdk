package com.casper.sdk.types.cltypes

import com.casper.sdk.json.deserialize.CLPublicKeyDeserializer
import com.casper.sdk.types.cltypes
import com.casper.sdk.util.HexUtils
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

/**
 * CLPublicKey : Casper system public key
 *
 * @param bytes
 */
@JsonDeserialize(`using` = classOf[CLPublicKeyDeserializer])
class CLPublicKey(
                   override val bytes: Array[Byte]
                 ) extends CLValue(bytes, CLType.PublicKey) {

  var keyAlgorithm: KeyAlgorithm = null

  /**
   * Constructor using a key String
   *
   * @param key
   */
  def this(key: String) = this(HexUtils.hex2Bytes(key))

  /**
   * Constructor using a key String and an Algorithm
   *
   * @param key
   * @param algo
   */
  def this(key: String, algo: KeyAlgorithm) = {
    this(key.getBytes())
    keyAlgorithm = algo
  }



}
