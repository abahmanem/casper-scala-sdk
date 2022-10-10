package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.CLPublicKey
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
/**
 * SeigniorageAllocation entity class
 *
 * @param Delegator
 * @param Validator
 * https://docs.rs/casper-types/1.4.4/casper_types/system/auction/enum.SeigniorageAllocation.html#
 */

sealed abstract class SeigniorageAllocation
case class  Delegator (
                        delegator_public_key: Option[CLPublicKey],
                        validator_public_key: Option[CLPublicKey],
                        amount: String
                      ) extends SeigniorageAllocation

object Delegator {
  implicit val decoder: Decoder[Delegator] = deriveDecoder[Delegator]
  implicit val encoder: Encoder[Delegator] = deriveEncoder[Delegator]
}



case class  Validator(
                        validator_public_key: Option[CLPublicKey],
                        amount: String
                      ) extends SeigniorageAllocation

object Validator {
  implicit val decoder: Decoder[Validator] = deriveDecoder[Validator]
  implicit val encoder: Encoder[Validator] = deriveEncoder[Validator]
}








object SeigniorageAllocation{

  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

  implicit val decoder:Decoder[SeigniorageAllocation] = deriveDecoder[SeigniorageAllocation]
  implicit val encoder:Encoder[SeigniorageAllocation] = deriveEncoder[SeigniorageAllocation]
}








