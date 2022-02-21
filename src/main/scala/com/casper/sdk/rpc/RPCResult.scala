package com.casper.sdk.rpc

import com.casper.sdk.domain
import com.casper.sdk.domain.Peer
import com.casper.sdk.json.deserialize.{CLPublicKeyDeserializer, RPCResultDeserializer}
import com.casper.sdk.rpc.RPCError
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id
import com.fasterxml.jackson.annotation.{JsonCreator, JsonSubTypes, JsonTypeInfo, JsonTypeName}
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

import scala.reflect.ClassTag

/**
 * Generic RPC Response Class, used to serialize/deserialize RPC Response payloads
 * @param jsonrpc
 * @param id
 * @param result
 * @param error
 * @param classTag$T$0
 * @tparam T Casper domaine type to be returned by the RPC request
 */

@JsonDeserialize(`using` = classOf[RPCResultDeserializer])
class RPCResult[T: ClassTag](
                                   jsonrpc: String,
                                   id: Long,
                                   val result: Option[T],
                                   val error: Option[RPCError] = None,

                                 ) {
  /**
   * Custom constructor with a generic Capser Result type and an RCPError
   * @param result
   * @param err
   */
  def this(result: T,  err: RPCError) = this("2.0", 1, Some(result), Some(err))


  /**
   * * Custom constructor with a generic Capser Result type
   * @param result
   */
  def this(result: T) = this("2.0", 1, Some(result), None)


  /**
   * Custom constructor with an RCPError
   * @param err
   */
  def this(err: RPCError) = this("2.0", 1, None, Some(err))
}
