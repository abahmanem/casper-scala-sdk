package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.URef
import com.casper.sdk.types.cltypes.CLType
/**
 * BidInfo entity class
 *
 * @param bonding_purse
 * @param staked_amount
 * @param delegation_rate
 * @param delegators
 * @param inactive
 */
case class BidInfo(
                    bonding_purse:Option[URef],
                    staked_amount: String,
                    delegation_rate: Int,
                    delegators: List[DelegatorInfo],
                    inactive: Boolean
                  )

object BidInfo{
  import io.circe.syntax._
  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
  implicit val decoder:Decoder[BidInfo] = deriveDecoder[BidInfo]
  implicit val encoder:Encoder[BidInfo] = deriveEncoder[BidInfo]
}
