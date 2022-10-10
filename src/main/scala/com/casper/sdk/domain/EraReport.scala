package com.casper.sdk.domain
import io.circe.generic.semiauto.deriveEncoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.{Decoder, Encoder}
/**
 * EraReport entity class
 *
 * @param equivocators
 * @param rewards
 * @param inactive_validators
 */
case class EraReport(
                      equivocators: Seq[String],
                      rewards: Seq[Reward],
                      inactive_validators: Seq[String]

                    )
object EraReport{
  implicit val decoder:Decoder[EraReport] = deriveDecoder[EraReport]
}