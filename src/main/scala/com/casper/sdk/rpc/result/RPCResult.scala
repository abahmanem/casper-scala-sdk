package com.casper.sdk.rpc.result

import com.casper.sdk.domain.*

import com.casper.sdk.rpc.RPCError
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id
import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo, JsonTypeName}

import scala.reflect.*

/**
 *
 * @param jsonrpc
 * @param id
 * @param result
 * @param error
 * @param classTag$T$0
 * @tparam T Casper domaine type to be returned by the RPC request
 */


@JsonTypeInfo(use = Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes(Array(
  new Type(value = classOf[PeerRPCResult], name = "com.casper.sdk.rpc.result.PeersResult"),
  new Type(value = classOf[StateRootHashRPCResult], name = "com.casper.sdk.rpc.result.StateRootHashResult"),
  new Type(value = classOf[AuctionStateRPCResult], name = "com.casper.sdk.rpc.result.AuctionStateResult"),
  new Type(value = classOf[BlockRPCResult], name = "com.casper.sdk.rpc.result.BlockResult")
)
)
case class RPCResult[T: ClassTag](
                                   jsonrpc: String,
                                   id: Long,
                                   result: Option[T],
                                   error: Option[RPCError] = None,

                                 ) {
  def this(result: T, err: RPCError) = this("2.0", 1, Some(result), Some(err))
}

/**
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

/**
 *
 * @param jsonrpc
 * @param id
 * @param result
 * @param error
 */
@JsonTypeName("com.casper.sdk.rpc.result.PeersResult")
class PeerRPCResult(
                  jsonrpc: String,
                  id: Long,
                  result: Option[PeersResult],
                  error: Option[RPCError] = None,
                ) extends RPCResult[PeersResult](jsonrpc, id, result, error)

/**
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
                 ) extends RPCResult[BlockResult](jsonrpc, id, result, error)

@JsonTypeName("com.casper.sdk.rpc.result.AuctionStateResult")
class AuctionStateRPCResult(
                              jsonrpc: String,
                              id: Long,
                              result: Option[AuctionStateResult],
                              error: Option[RPCError] = None,
                            ) extends RPCResult[AuctionStateResult](jsonrpc, id, result, error)


/**
 * RPCResult type Getter
 *
 * @tparam T : Capser Type
 */

trait ResultTypeGetter[T] {
  def root: RPCResult[T]
}

object ResultGetter {
  implicit final val peerResultGetter: ResultTypeGetter[PeersResult] = new ResultTypeGetter {
    override final val root: RPCResult[PeersResult] = new PeerRPCResult("2.0", 1, None, None)
  }
  implicit final val StateRootHashResultGetter: ResultTypeGetter[StateRootHashResult] = new ResultTypeGetter {
    override final val root: RPCResult[StateRootHashResult] = new StateRootHashRPCResult("2.0", 1, None, None)
  }
  // TODO All the other instances
}
