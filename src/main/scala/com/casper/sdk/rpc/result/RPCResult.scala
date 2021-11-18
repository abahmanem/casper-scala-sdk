package com.casper.sdk.rpc.result

import com.casper.sdk.domain.*

import com.casper.sdk.rpc.RPCError
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id
import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo, JsonTypeName}

import scala.reflect.*

/**
 * Generic RPC Response Class, used to serialize/deserialize RPC Response payloads
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
  new Type(value = classOf[PeersRPCResult], name = "com.casper.sdk.rpc.result.PeersResult"),
  new Type(value = classOf[StateRootHashRPCResult], name = "com.casper.sdk.rpc.result.StateRootHashResult"),
  new Type(value = classOf[AuctionStateRPCResult], name = "com.casper.sdk.rpc.result.AuctionStateResult"),
  new Type(value = classOf[BlockRPCResult], name = "com.casper.sdk.rpc.result.BlockResult"),
  new Type(value = classOf[NodeStatusRPCResult], name = "com.casper.sdk.domain.NodeStatus"),
  new Type(value = classOf[BlockTransfertRPCResult], name = "com.casper.sdk.rpc.result.BlockTransfertResult")

)
)
case class RPCResult[T: ClassTag](
                                   jsonrpc: String,
                                   id: Long,
                                   result: Option[T],
                                   error: Option[RPCError] = None,

                                 ) {
  /**
   * Custom constructor with a generic Capser type and an error
   * @param result
   * @param err
   */
  def this(result: T, err: RPCError) = this("2.0", 1, Some(result), Some(err))
}
