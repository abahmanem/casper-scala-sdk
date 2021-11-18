package com.casper.sdk.rpc.result

import com.casper.sdk.rpc.RPCError
import com.fasterxml.jackson.annotation.JsonTypeName

/**
 * NodeStatusRPCResult Class  to serialize/deserialize RPC Response payloads into  NodeStatus object
 * @param jsonrpc
 * @param id
 * @param result
 * @param error
 */

@JsonTypeName("com.casper.sdk.domain.NodeStatus")
class NodeStatusRPCResult(
                           jsonrpc: String,
                           id: Long,
                           result: Option[com.casper.sdk.domain.NodeStatus],
                           error: Option[RPCError] = None,
                         ) extends RPCResult[com.casper.sdk.domain.NodeStatus](jsonrpc, id, result, error)