package com.casper.sdk.domain

/**
 * EraValidator entity class
 * @param era_id
 * @param validator_weights
 */

case class EraValidator(
                         era_id: Int,
                         validator_weights: Seq[ValidatorWeight]
                       )

object EraValidator{
  import io.circe.syntax._
  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
  implicit val decoder:Decoder[EraValidator] = deriveDecoder[EraValidator]
  implicit val encoder:Encoder[EraValidator] = deriveEncoder[EraValidator]
}
