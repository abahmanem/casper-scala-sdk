package com.casper.sdk.domain
import io.circe.generic.semiauto.deriveEncoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.{Decoder, Encoder}
/**
 * EraEnd entity class
 *
 * @param era_report
 * @param next_era_validator_weights
 */

case class EraEnd(
                   era_report: EraReport,
                   next_era_validator_weights: List[ValidatorWeight]
                 )


object EraEnd{

  implicit val decoder:Decoder[EraEnd] = deriveDecoder[EraEnd]
  implicit val encoder:Encoder[EraEnd] = deriveEncoder[EraEnd]

}