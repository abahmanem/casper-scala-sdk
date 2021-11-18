package com.casper.sdk.rpc.result

import com.casper.sdk.domain.Block
import com.casper.sdk.rpc.RPCError
import com.fasterxml.jackson.annotation.JsonTypeName

/**
 * BlockRPCResult Class  to serialize/deserialize RPC Response payloads into  BlockResult object
 *
 * @param jsonrpc
 * @param id
 * @param result
 * @param error
 */
@JsonTypeName("com.casper.sdk.rpc.result.BlockResult")
class BlockRPCResult(
                      jsonrpc: String,
                      id: Long,
                      result: Option[BlockResult],
                      error: Option[RPCError] = None,
                    ) extends RPCResult[BlockResult](jsonrpc, id, result, error) {

  case class BlockResult
  (api_version: String,
   block: Block
  ) //extends Result(api_version)

}