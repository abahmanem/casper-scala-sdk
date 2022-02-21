package com.casper.sdk.rpc

import com.casper.sdk.rpc.exceptions._
import com.casper.sdk.rpc.http.HttpRPCService
import com.casper.sdk.util.JsonConverter
import scala.reflect.ClassTag
import scala.reflect
import scala.util.{Failure, Success, Try}
import scala.concurrent.{Await, Future}
import concurrent.duration.DurationInt

/**
 * RPC client class
 *
 */
trait RPCCommand(rpcService: RPCService) {


  /**
   * Performs the RPC call
   *
   * @param method
   * @param params
   * @tparam T
   * @return Casper Result Type T
   */
  def call[T: ClassTag](method: Method, params: Any*): Try[T] = {
    val res: Option[RPCResult[T]] = rpcService.send[T](RPCRequest(RPCRequest.id.incrementAndGet(), method.name, params: _*))
    result(res, method, params)
  }


  /**
   * non blocking RPC call
   *
   * @param method : rpc method
   * @param params : parameters
   * @tparam T : Casper type
   * @return Try[T]
   */
  def callAsync[T: ClassTag](method: Method, params: Any*): Try[T] = {
    val res: Future[Option[RPCResult[T]]] = rpcService.sendAsync[T](RPCRequest(RPCRequest.id.incrementAndGet(), method.name, params: _*))
    val ress = Await.result(res, 10.seconds)
    result(ress, method, params)
  }


  /**
   * exctract Try[T] from  Option[RPCResult[T]
   *
   * @param res
   * @param method
   * @param params
   * @tparam T
   * @return Try[T]
   */
  def result[T: ClassTag](res: Option[RPCResult[T]], method: Method, params: Any*): Try[T] = {
    res match {
      case Some(x) => {
        x.error match {
          case None =>
            x.result match {
              case Some(x) if x!=null =>  Success(x)
              case Some(_)|Some(null)| None => Failure(RPCException(s"No result was returned when invoking RPC method: $method with params: $params", RPCError.NO_RESULTS))
            }
          case Some(err) => Failure(RPCException(s"An error occured when invoking RPC method: ${method.name} with params: $params", err))
        }
      }
      case None => Failure(RPCException(s"No result was returned when invoking RPC method: $method with params: $params", RPCError.NO_RESULTS))
    }
  }
}