package com.casper.sdk.result
import  com.casper.sdk.core.Error
import com.casper.sdk.result.RPCResult


case class StateGetBalanceDetailResult(
  api_version : String,
  balance_value : Long,
  merkle_proof : String
)

case class StateGetBalanceResult
(
  jsonrpc: String,
  id: Long,
  result: Option[StateGetBalanceDetailResult],
  error: Option[Error] = None,
) extends RPCResult(jsonrpc, id, result, error)
