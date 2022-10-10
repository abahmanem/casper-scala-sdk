package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.CLPublicKey

/**
 * Bid entity class : https://docs.rs/casper-types/1.4.4/casper_types/system/auction/struct.Bid.html
 *
 * @param public_key
 * @param bid
 */

case class Bid(
                public_key: Option[CLPublicKey],
                bid: BidInfo,
               )

object Bid{
  import io.circe.syntax._
  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
  implicit val decoder:Decoder[Bid] = deriveDecoder[Bid]
  implicit val encoder:Encoder[Bid] = deriveEncoder[Bid]
}

