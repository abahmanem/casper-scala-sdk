package com.casper.sdk.rpc

import cats.Id
import com.casper.sdk.rpc.exceptions.*
import com.casper.sdk.rpc.http.HttpRPCService
import com.casper.sdk.util.IdInstance

import scala.reflect.*

/**
 * RPC client class
 *
 */
trait RPCCommand(rpcService: RPCService)(implicit id: IdInstance) {


  /**
   * Performs the RPC call
   * @param method
   * @param params
   * @tparam T
   * @return Casper Result Type T
   */
  def call[T: ClassTag](method: Method, params: Any*): T = {

    val res = rpcService.send[T](RPCRequest(RPCRequest.id.incrementAndGet(), method.name, params: _*))
    res.error match {
      case None =>
        res.result match {
          case Some(null) => id.pure(None)
          case result => id.pure(result)
        }
      case Some(err) => id.throwError(RPCException(s"An error occured when invoking RPC method: ${method.name} with params: $params", err))
    }
    match
    {
      case Some(x) if x != null => id.pure(x)
      case Some(_) => id.throwError(RPCException(s"No result was returned when invoking RPC method: $method with params: $params", RPCError.NO_RESULTS))
      case None => id.throwError(RPCException(s"No result was returned when invoking RPC method: $method with params: $params", RPCError.NO_RESULTS))
    }
  }

  //TODO implement async calls
  //def callAsync[T: ClassTag](method: String, params: Any*) : T = {}

}