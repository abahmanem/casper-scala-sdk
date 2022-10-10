package com.casper.sdk.types.cltypes


import com.casper.sdk.types.cltypes.CLPublicKey
import com.casper.sdk.util.{ByteUtils, HexUtils}
import scala.util.{Failure, Success, Try}
/**
 * Signature Type
 * @param bytes
 * @param keyAlgo
 */
case class Signature (
                  bytes: Array[Byte],
                  keyAlgo: KeyAlgorithm
                ){

  /**
   * format to Hex account , ie : 0106cA7c39cD272DbF21a86EeB3B36B7c26E2e9b94af64292419f7862936bcA2cA, 01 being tag bytes
   *
   * @return
   */
  def formatAsHexAccount: Option[String] = Try(HexUtils.toHex(formatAsByteAccount).getOrElse("")).toOption

  /**
   * format to Byte array with algorithm
   *
   * @return
   */
  def formatAsByteAccount: Array[Byte] = ByteUtils.join(Array.fill(1)(keyAlgo.bits.toByte), bytes)
}

/**
 * Companion object
 */
object Signature{

  def apply(signature:String): Option[Signature] =
    Try(new Signature (CLPublicKey.dropAlgorithmBytes(HexUtils.fromHex(signature).get), KeyAlgorithm.fromId(signature.charAt(1).asDigit).getOrElse(KeyAlgorithm.ED25519))).toOption

  import io.circe.{Decoder, Encoder, HCursor, Json}
  implicit val decoder: Decoder[Option[Signature]] = Decoder.decodeString.emapTry {
    str => Try(Signature(str))
  }
  implicit val encoder: Encoder[Signature] = (signature: Signature) => Encoder.encodeString(signature.formatAsHexAccount.getOrElse(""))
}