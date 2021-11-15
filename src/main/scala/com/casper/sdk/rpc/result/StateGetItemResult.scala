package com.casper.sdk.rpc.result
import com.casper.sdk.rpc.RPCError
import com.casper.sdk.rpc.result.RPCResult


/*

case class StateGetItemContractNamedKeyResult(
  name : String,
  key : String
)

//TODO complete the CL_Type
case class StateGetItemContractEntryPointArgResult(
  name : String
)

case class StateGetItemContractEntryPointResult(
  name : String,
  args : List[StateGetItemContractEntryPointArgResult],
  ret : String,
  access : String,
  entry_point_type : String
)

case class StateGetItemContractDetailResult(
  contract_package_hash : String,
  contract_wasm_hash : String,
  named_keys : List[StateGetItemContractNamedKeyResult],
  entry_points : List[StateGetItemContractEntryPointResult],
  protocol_version : String
)

case class StateGetItemContractResult(
  contract : StateGetItemContractDetailResult
)

case class StateGetItemMapResult(
  api_version : String,
  stored_value : StateGetItemContractResult,
  merkle_proof : String
)

 class StateGetItemResult
(
  jsonrpc: String,
  id: Long,
  result: Option[StateGetItemMapResult],
  error: Option[Error] = None,
) extends RPCResult(jsonrpc, id, result, error)
*/