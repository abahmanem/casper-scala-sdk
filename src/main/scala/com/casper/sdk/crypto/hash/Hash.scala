package com.casper.sdk.crypto.hash


import com.casper.sdk.util.HexUtils
import scala.util.Try

/**
 * Hash class
 *
 * @param hash
 */
case class Hash(
                 hash: Array[Byte]
               ) {
  /**
   * constructor with hex string
   *
   * @param stringHash
   */
    def formatAsHexString: Option[String] = Try(HexUtils.toHex(hash).get).toOption
}

object Hash{

  /**
   *
   * @param hex
   * @return CLPublicKey
   */
  def apply(hex: String): Option[Hash] = HexUtils.fromHex(hex).map(bytes => Hash(bytes))
  import io.circe.{Decoder, Encoder}

  implicit val decoderOption: Decoder[Option[Hash]] = Decoder.decodeString.emapTry {
    str => Try(Hash(str))
  }

  implicit val decoder: Decoder[Hash] = Decoder.decodeString.emapTry {
    str => Try(Hash(HexUtils.fromHex(str).get))
  }
  implicit val encoder: Encoder[Hash] = (hash: Hash) =>    Encoder.encodeString(HexUtils.toHex(hash.hash).getOrElse(""))
}
