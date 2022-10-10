package com.casper.sdk.rpc.result

case class BalanceResult(api_version:String, balance_value:String)

object BalanceResult{
  import io.circe.Decoder
  import io.circe.generic.semiauto.deriveDecoder
  implicit val decoder:Decoder[BalanceResult] = deriveDecoder[BalanceResult]
}

