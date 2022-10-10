package com.casper.sdk.rpc.result

import com.casper.sdk.domain.StoredValue

case class StateItemResult(api_version:String, stored_value:StoredValue)

object StateItemResult{
  import io.circe.syntax._
  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
  implicit val decoder:Decoder[StateItemResult] = deriveDecoder[StateItemResult]
 // implicit val encoder:Encoder[StateItemResult] = deriveEncoder[StateItemResult]
}
