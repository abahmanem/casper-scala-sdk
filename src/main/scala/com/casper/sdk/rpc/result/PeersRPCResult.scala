package com.casper.sdk.rpc.result

import com.casper.sdk.domain.Peer
import com.casper.sdk.rpc.RPCError
import com.fasterxml.jackson.annotation.JsonTypeName

/**
 * PeersRPCResult Class  to serialize/deserialize RPC Response payloads into  PeersResult object
 *
 * @param jsonrpc
 * @param id
 * @param result
 * @param error
 */
@JsonTypeName("com.casper.sdk.rpc.result.PeersResult")
class PeersRPCResult(
                     jsonrpc: String,
                     id: Long,
                     result: Option[PeersResult],
                     error: Option[RPCError] = None,
                   ) extends RPCResult[PeersResult](jsonrpc, id, result, error) 