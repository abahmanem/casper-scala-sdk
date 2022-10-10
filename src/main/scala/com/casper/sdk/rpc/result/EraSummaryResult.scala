package com.casper.sdk.rpc.result

import com.casper.sdk.domain.EraSummary
import io.circe.generic.semiauto.deriveDecoder

case class EraSummaryResult(api_version:String, era_summary:Option[EraSummary])

object EraSummaryResult{
  import io.circe.{Decoder, Encoder, HCursor, Json}
  implicit val decoder:Decoder[EraSummaryResult] = deriveDecoder[EraSummaryResult]
  //implicit val encoder:Encoder[DeployResult] = deriveEncoder[DeployResult]
}