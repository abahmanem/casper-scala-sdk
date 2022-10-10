package com.casper.sdk.domain

/**
 * ActionThresholds entity class : https://docs.rs/casper-types/1.4.4/casper_types/account/action_thresholds/struct.ActionThresholds.html
 * @param deployment
 * @param key_management
 */
case class ActionThresholds(
                             deployment: Int,
                             key_management:Int
                           )

object ActionThresholds{

  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

  implicit val decoder:Decoder[ActionThresholds] = deriveDecoder[ActionThresholds]
  implicit val encoder:Encoder[ActionThresholds] = deriveEncoder[ActionThresholds]
}
