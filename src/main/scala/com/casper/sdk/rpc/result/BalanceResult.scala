package com.casper.sdk.rpc.result

/**
 * getBalance RPC response
 * @param api_version
 * @param balance_value
 */
case class BalanceResult(api_version:String, balance_value:String)

object BalanceResult{
  import io.circe.Decoder
  import io.circe.generic.semiauto.deriveDecoder
  implicit val decoder:Decoder[BalanceResult] = deriveDecoder[BalanceResult]
}

