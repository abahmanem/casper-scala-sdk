package com.casper.sdk.types.cltypes

import com.casper.sdk.json.deserialize.SignatureDeserializer
import com.casper.sdk.types.cltypes.CLPublicKey
import com.casper.sdk.types.cltypes.CLPublicKey.dropAlgorithmBytes
import com.casper.sdk.util.HexUtils
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

/**
 * Signature Type
 * @param bytes
 * @param keyAlgo
 */
@JsonDeserialize(`using` = classOf[SignatureDeserializer])
class Signature (
                  bytes: Array[Byte],
                  keyAlgo: KeyAlgorithm
                )
  extends CLPublicKey(bytes, keyAlgo) {
  /**
   * Constructor from a hex String
   * @param signature
   */
 def  this(signature: String) = this(dropAlgorithmBytes(HexUtils.fromHex(signature)),KeyAlgorithm.fromId(signature.charAt(1)))
}

