package com.casper.sdk.types.cltypes

import com.casper.sdk.json.deserialize.SignatureDeserializer
import com.casper.sdk.types.cltypes.CLPublicKey
import com.casper.sdk.util.HexUtils
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import scala.util.{Try, Success, Failure}
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
 //def  this(signature: String) = this(CLPublicKey.dropAlgorithmBytes(HexUtils.fromHex(signature).get),KeyAlgorithm.fromId(signature.charAt(1).asDigit).get)
}

/**
 * Companion object
 */
object Signature{
  def apply(signature:String): Option[Signature] = Try {
    new Signature(CLPublicKey.dropAlgorithmBytes(HexUtils.fromHex(signature).get),KeyAlgorithm.fromId(signature.charAt(1).asDigit).get)
  } match {
    case Success(x) => Some(x)
    case Failure(err) =>         None
  }
}