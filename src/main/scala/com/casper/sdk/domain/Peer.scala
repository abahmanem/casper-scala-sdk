package com.casper.sdk.domain
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.deriveEncoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * Peer entity class
 *
 * @param node_id
 * @param address
 */
case class Peer(
                 node_id: String,
                 address: String
               )

object Peer{
  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.deriveEncoder
  import io.circe.generic.semiauto.deriveDecoder

  implicit val decoder:Decoder[Peer] = deriveDecoder[Peer]
  implicit val encoder:Encoder[Peer] = deriveEncoder[Peer]
}
