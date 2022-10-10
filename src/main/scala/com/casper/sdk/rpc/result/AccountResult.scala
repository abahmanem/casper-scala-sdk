package com.casper.sdk.rpc.result

import com.casper.sdk.domain.Account

/**
 * state_get_account RPC response
 * @param api_version
 * @param account
 */
case class AccountResult(api_version:String, account:Account)
object AccountResult{
  import io.circe.Decoder
  import io.circe.generic.semiauto.deriveDecoder
  implicit val decoder:Decoder[AccountResult] = deriveDecoder[AccountResult]
}
