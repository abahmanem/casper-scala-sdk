package com.casper.sdk.rpc.result

import com.casper.sdk.rpc.result.PeerResult
import com.casper.sdk.domain.Peer

case class PeerResult(api_version: String, peers: List[Peer])


object PeerResult{

  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
  import io.circe.syntax._
  import io.circe.{Decoder, Encoder}
  implicit val decoder:Decoder[PeerResult] = deriveDecoder[PeerResult]
  implicit val encoder:Encoder[PeerResult] = deriveEncoder[PeerResult]
}
