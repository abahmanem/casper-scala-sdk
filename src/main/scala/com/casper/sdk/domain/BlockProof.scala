package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.CLPublicKey
import io.circe.generic.semiauto.deriveEncoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.{Decoder, Encoder}
/**
 * BlockProof entity class
 *
 * @param public_key
 * @param signature
 */
case class BlockProof(
                       public_key: Option[CLPublicKey],
                      // public_key: String,
                       signature: String
                     )

object BlockProof{

  implicit val decoder:Decoder[BlockProof] = deriveDecoder[BlockProof]
  implicit val encoder:Encoder[BlockProof] = deriveEncoder[BlockProof]
}