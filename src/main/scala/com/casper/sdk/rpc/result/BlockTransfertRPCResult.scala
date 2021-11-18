package com.casper.sdk.rpc.result

import com.casper.sdk.domain.Transfer
import com.casper.sdk.rpc.RPCError
import com.fasterxml.jackson.annotation.JsonTypeName

/**
 * BlockTransfertRPCResult Class  to serialize/deserialize RPC Response payloads into  BlockTransfertResult object
 * @param jsonrpc
 * @param id
 * @param result
 * @param error
 */

@JsonTypeName("com.casper.sdk.rpc.result.BlockTransfertResult")
class BlockTransfertRPCResult(
                               jsonrpc: String,
                               id: Long,
                               result: Option[BlockTransfertResult],
                               error: Option[RPCError] = None,
                             ) extends RPCResult[BlockTransfertResult](jsonrpc, id, result, error) 
