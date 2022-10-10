package com.casper.sdk.domain

/**
 * AuctionState entity class
 * @param state_root_hash
 * @param block_height
 * @param era_validators
 * @param bids
 */
case class AuctionState(
                         state_root_hash: String,
                         block_height: Int,
                         era_validators: List[EraValidator],
                         bids: List[Bid]
                       )
object AuctionState{
  import io.circe.syntax._
  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
  implicit val decoder:Decoder[AuctionState] = deriveDecoder[AuctionState]
  implicit val encoder:Encoder[AuctionState] = deriveEncoder[AuctionState]
}
