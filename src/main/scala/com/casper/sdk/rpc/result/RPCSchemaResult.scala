package com.casper.sdk.rpc.result

import io.circe.Json

case class RPCSchemaResult(api_version: String, name:String, schema: Json)


object RPCSchemaResult{
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
  import io.circe.syntax._
  import io.circe.{Decoder, Encoder}
  implicit val decoder:Decoder[RPCSchemaResult] = deriveDecoder[RPCSchemaResult]
  //implicit val encoder:Encoder[PeerResult] = deriveEncoder[RPCSchemaResult]
}
