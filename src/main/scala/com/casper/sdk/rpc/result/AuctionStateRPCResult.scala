package com.casper.sdk.rpc.result

import com.casper.sdk.domain.AuctionState
import com.casper.sdk.rpc.RPCError
import com.fasterxml.jackson.annotation.JsonTypeName

/**
 * AuctionStateRPCResult Class  to serialize/deserialize RPC Response payloads into  AuctionStateResult object
 *
 * @param jsonrpc
 * @param id
 * @param result
 * @param error
 */

@JsonTypeName("com.casper.sdk.rpc.result.AuctionStateResult")
class AuctionStateRPCResult(
                             jsonrpc: String,
                             id: Long,
                             result: Option[AuctionStateResult],
                             error: Option[RPCError] = None,
                           ) extends RPCResult[AuctionStateResult](jsonrpc, id, result, error)


