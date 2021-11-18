package com.casper.sdk.rpc

import java.util.concurrent.atomic.AtomicInteger

/**
 * RPC Request class, used to serialize/deserilaize RPC request payloads
 * @param id
 * @param method
 * @param params
 * @param jsonrpc
 */
case class RPCRequest(id: Long,
                      method: String,
                      params: Seq[Any],
                      jsonrpc: String = "2.0")

object RPCRequest {
  val id = new AtomicInteger()

  def apply(id: Long, method: String, params: Any*): RPCRequest = new RPCRequest(id, method, params.toList)
}




