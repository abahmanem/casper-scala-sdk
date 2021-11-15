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
  new Type(value = classOf[PeerResult], name = "com.casper.sdk.domain.Peers"),
  new Type(value = classOf[StateRootHashResult], name = "com.casper.sdk.domain.StateRootHash"),
  new Type(value = classOf[AuctionState], name = "com.casper.sdk.domain.AuctionState"),
  new Type(value = classOf[BlockResult], name = "com.casper.sdk.domain.Block"),
))
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
@JsonTypeName("com.casper.sdk.domain.StateRootHash")
class StateRootHashResult(
                           jsonrpc: String,
                           id: Long,
                           result: Option[StateRootHash],
                           error: Option[RPCError] = None,
                         ) extends RPCResult(jsonrpc, id, result, error)

/**
 *
 * @param jsonrpc
 * @param id
 * @param result
 * @param error
 */
@JsonTypeName("com.casper.sdk.domain.Peers")
class PeerResult(
                  jsonrpc: String,
                  id: Long,
                  result: Option[Peers],
                  error: Option[RPCError] = None,
                ) extends RPCResult[Peers](jsonrpc, id, result, error)

/**
 *
 * @param jsonrpc
 * @param id
 * @param result
 * @param error
 */
@JsonTypeName("com.casper.sdk.domain.Block")
class BlockResult(
                   jsonrpc: String,
                   id: Long,
                   result: Option[Block],
                   error: Option[RPCError] = None,
                 ) extends RPCResult[Block](jsonrpc, id, result, error)

/**
 *
 * @param jsonrpc
 * @param id
 * @param result
 * @param error
 */
@JsonTypeName("com.casper.sdk.domain.AuctionState")
class AuctionStataInfoResult(
                              jsonrpc: String,
                              id: Long,
                              result: Option[AuctionState],
                              error: Option[RPCError] = None,
                            ) extends RPCResult[AuctionState](jsonrpc, id, result, error)


//TODO add other Result Wrappers


/**
 * RPCResult type Getter
 *
 * @tparam T : Capser Type
 */

trait ResultTypeGetter[T] {
  def root: RPCResult[T]
}

object ResultGetter {
  implicit final val peerResultGetter: ResultTypeGetter[Peers] = new ResultTypeGetter {
    override final val root: RPCResult[Peers] = new PeerResult("2.0", 1, None, None)
  }
  implicit final val StateRootHashResultGetter: ResultTypeGetter[StateRootHash] = new ResultTypeGetter {
    override final val root: RPCResult[StateRootHash] = new StateRootHashResult("2.0", 1, None, None)
  }
  // TODO All the other instances
}
