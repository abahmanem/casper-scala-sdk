package com.casper.sdk.rpc

import java.util.concurrent.atomic.AtomicInteger


case class RPCRequest(id: Long,
                      method: String,
                      params: Seq[Any],
                      jsonrpc: String = "2.0")

object RPCRequest {
  val id = new AtomicInteger()

  def apply(id: Long, method: String, params: Any*): RPCRequest = new RPCRequest(id, method, params.toList)
}




