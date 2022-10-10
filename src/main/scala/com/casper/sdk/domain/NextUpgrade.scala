package com.casper.sdk.domain

/**
 * NextUpgrade entity class
 * @param next_upgrade
 * @param build_version
 */
case class NextUpgrade(
                        next_upgrade: Option[String],
                        build_version: String
                      )
object NextUpgrade{
  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.deriveEncoder
  import io.circe.generic.semiauto.deriveDecoder

  implicit val decoder:Decoder[NextUpgrade] = deriveDecoder[NextUpgrade]
  implicit val encoder:Encoder[NextUpgrade] = deriveEncoder[NextUpgrade]
}
