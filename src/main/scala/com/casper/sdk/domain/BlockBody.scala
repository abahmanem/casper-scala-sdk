package com.casper.sdk.domain
import io.circe.generic.semiauto.deriveEncoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.{Decoder, Encoder}
/**
 * BlockBody entity class
 * @param proposer
 * @param deploy_hashes
 * @param transfer_hashes
 */
case class BlockBody(
                      proposer: String,
                      deploy_hashes: Seq[String],
                      transfer_hashes: Seq[String]
                    )

object BlockBody{

  implicit val decoder:Decoder[BlockBody] = deriveDecoder[BlockBody]
  implicit val encoder:Encoder[BlockBody] = deriveEncoder[BlockBody]
}