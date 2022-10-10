package com.casper.sdk.rpc

import java.util.concurrent.atomic.AtomicInteger
import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}
import io.circe._,io.circe.syntax._

/**
 * RPC Request class, used to serialize/deserilaize RPC request payloads
 * @param id
 * @param method RPC method
 * @param params params of the call
 * @param jsonrpc jsonrpc version
 */
case class RPCRequest(
                      id: Long,
                      method: String,
                      params: Seq[Json],
                      jsonrpc: String = "2.0")

object RPCRequest {
  val id = new AtomicInteger()
  def apply(id: Long, method: String, params: Json*): RPCRequest = new RPCRequest(id, method, params.toList)
  implicit val decoder:Decoder[RPCRequest] = deriveDecoder[RPCRequest]
  implicit val encoder:Encoder[RPCRequest] = deriveEncoder[RPCRequest]
}




