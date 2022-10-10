package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.URef

/**
 * Group
 * @param group
 * @param keys
 */
case class Group(
                  group : String,
                  keys: Seq[Option[URef]]
                )

object Group{

  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

  implicit val decoder:Decoder[Group] = deriveDecoder[Group]
  implicit val encoder:Encoder[Group] = deriveEncoder[Group]
}
