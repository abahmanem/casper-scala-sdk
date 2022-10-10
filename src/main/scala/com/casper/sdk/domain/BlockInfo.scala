package com.casper.sdk.domain

/**
 * BlockInfo entity class
 *
 * @param hash
 * @param timestamp
 * @param era_id
 * @param height
 * @param state_root_hash
 * @param creator
 */
case class BlockInfo(
                      hash: String,
                      timestamp: String,
                      era_id: BigInt,
                      height: BigInt,
                      state_root_hash: String,
                      creator: String
                    )


object BlockInfo{
  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.deriveEncoder
  import io.circe.generic.semiauto.deriveDecoder

  implicit val decoder:Decoder[BlockInfo] = deriveDecoder[BlockInfo]
  implicit val encoder:Encoder[BlockInfo] = deriveEncoder[BlockInfo]
}