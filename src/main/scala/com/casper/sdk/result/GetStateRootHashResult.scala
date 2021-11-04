package com.casper.sdk.result
import  com.casper.sdk.core.Error
import com.casper.sdk.result.RPCResult


case class StateRootHashResult
(
  api_version : String,
  state_root_hash : String
)


case class GetStateRootHashResult
(
  jsonrpc: String,
  id: Long,
  result: Option[StateRootHashResult],
  error: Option[Error] = None,
) extends RPCResult(jsonrpc, id, result, error)
