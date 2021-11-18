package com.casper.sdk.rpc.result

import com.casper.sdk.rpc.RPCError
import com.fasterxml.jackson.annotation.JsonTypeName

/**
 * StateRootHashRPCResult Class  to serialize/deserialize RPC Response payloads into  StateRootHashResult object
 *
 * @param jsonrpc
 * @param id
 * @param result
 * @param error
 */
@JsonTypeName("com.casper.sdk.rpc.result.StateRootHashResult")
class StateRootHashRPCResult(
                              jsonrpc: String,
                              id: Long,
                              result: Option[StateRootHashResult],
                              error: Option[RPCError] = None,
                            ) extends RPCResult[StateRootHashResult](jsonrpc, id, result, error)
