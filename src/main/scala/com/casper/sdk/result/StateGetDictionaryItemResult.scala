package com.casper.sdk.result
import  com.casper.sdk.core.Error
import com.casper.sdk.result.RPCResult



//TODO Define the CLValue type
case class StateGetDictionaryItemMapResult(
  api_version : String,
  dictionary_key : String,
  // stored_value : StateGetDictionaryItemStoredValueResult,
  merkle_proof: String
)

case class StateGetDictionaryItemResult
(
  jsonrpc: String,
  id: Long,
  result: Option[StateGetDictionaryItemMapResult],
  error: Option[Error] = None,
) extends RPCResult(jsonrpc, id, result, error)
