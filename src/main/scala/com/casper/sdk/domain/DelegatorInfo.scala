package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.{CLPublicKey, URef}

/**
 * DelegatorInfo entity class
 *
 * @param public_key
 * @param staked_amount
 * @param bonding_purse
 * @param delegatee
 */
case class DelegatorInfo(
                      public_key: Option[CLPublicKey],
                      staked_amount: BigInt,
                      bonding_purse: Option[URef],
                      delegatee: String
                    )

object DelegatorInfo{
  import io.circe.syntax._
  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
  implicit val decoder:Decoder[DelegatorInfo] = deriveDecoder[DelegatorInfo]
  implicit val encoder:Encoder[DelegatorInfo] = deriveEncoder[DelegatorInfo]
}
