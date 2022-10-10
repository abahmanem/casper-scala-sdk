package com.casper.sdk.domain

/**
 * NamedKey entity class
 * @param name
 * @param key
 */
case class NamedKey(
                    name:String,
                    key: String
                   )

object NamedKey{

  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

  implicit val decoder:Decoder[NamedKey] = deriveDecoder[NamedKey]

  implicit val encoder:Encoder[NamedKey] = deriveEncoder[NamedKey]
}
