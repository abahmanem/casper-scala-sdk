package com.casper.sdk.rpc.result
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax._
import io.circe.{Decoder, Encoder}
case class StateRootHashResult(api_version: String, state_root_hash: String)
object StateRootHashResult{
  implicit val decoder:Decoder[StateRootHashResult] = deriveDecoder[StateRootHashResult]
  implicit val encoder:Encoder[StateRootHashResult] = deriveEncoder[StateRootHashResult]
}
