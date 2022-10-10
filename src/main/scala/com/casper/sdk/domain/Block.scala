package com.casper.sdk.domain

import io.circe.generic.semiauto.deriveEncoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.{Decoder, Encoder}
/**
 * Block entity class
 * @param hash
 * @param header
 * @param body
 * @param proofs
 */

case class Block(
                  hash: String,
                  header: BlockHeader,
                  body: BlockBody,
                  proofs: Seq[BlockProof]
                )

object Block{
  implicit val decoder:Decoder[Block] = deriveDecoder[Block]
  implicit val encoder:Encoder[Block] = deriveEncoder[Block]
}