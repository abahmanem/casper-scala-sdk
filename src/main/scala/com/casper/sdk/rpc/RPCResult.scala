package com.casper.sdk.rpc

import com.casper.sdk.domain.Block
import com.casper.sdk.domain.Peer
import com.casper.sdk.rpc.RPCError
import cats.syntax.either._
import com.casper.sdk.rpc.result.{PeerResult, StateRootHashResult,BlockResult}
import io.circe._
import io.circe.parser._
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._
/**
 * Generic RPC Response Class, used to serialize/deserialize RPC Response payloads
 * @param jsonrpc
 * @param id
 * @param result
 * @param error
 * @param classTag$T$0
 * @tparam T Casper domaine type to be returned by the RPC request
 */

case class RPCResult[T](
                                   jsonrpc: String,
                                   id:Option[Long],
                                   result: Option[T],
                                   error: Option[RPCError] = None,

                                 ) {
  /**
   * Custom constructor with a generic Capser Result type and an RCPError
   * @param result
   * @param err
   */
  def this(result: T,  err: RPCError) = this("2.0", Some(1), Some(result), Some(err))


  /**
   * * Custom constructor with a generic Capser Result type
   * @param result
   */
  def this(result: T) = this("2.0", Some(1), Some(result), None)


  /**
   * Custom constructor with an RCPError
   * @param err
   */
  def this(err: RPCError) = this("2.0", Some(1), None, Some(err))
}
object RPCResult {
  implicit def RcpEncoder[T: Encoder]: Encoder[RPCResult[T]] = deriveEncoder
  implicit def RcpDecoder[T: Decoder]: Decoder[RPCResult[T]] = deriveDecoder

}

