package com.casper.sdk.rpc

import com.casper.sdk.rpc.exceptions._
import com.casper.sdk.rpc.http.HttpRPCService
import scala.reflect.ClassTag
import scala.reflect
import scala.util.{Failure, Success, Try}
import scala.concurrent.{Await, Future}
import concurrent.duration.DurationInt
import io.circe.Decoder
import io.circe._, io.circe.syntax._

/**
 * RPC client class
 *
 */
class RPCCommand(rpcService: RPCService) {


  /**
   * Performs the RPC call
   *
   * @param method
   * @param params
   * @tparam T
   * @return Casper Result Type T
   */
  def call[T: ClassTag](method: Method, params: Json*)(implicit decoder: Decoder[T]): Try[T] = {
    val res: Try[RPCResult[T]] = rpcService.send[T](RPCRequest(RPCRequest.id.incrementAndGet(), method.name, params))
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
  def callAsync[T: ClassTag](method: Method, params: Seq[Json])(implicit decoder: Decoder[T]): Try[T] = {
    val future: Future[Try[RPCResult[T]]] = rpcService.sendAsync[T](RPCRequest(RPCRequest.id.incrementAndGet(), method.name, params))
    val await = Await.result(future, 10.seconds)
    result(await, method, params)
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
  def result[T: ClassTag](res: Try[RPCResult[T]], method: Method, params: Any*): Try[T] = {
    res match {
      case Success(x) => x.error match {
        case None =>
          x.result match {
            case Some(x) if x != null => Success(x)
            case Some(_) | Some(null) | None => Failure(RPCException(s"No result was returned when invoking RPC method: $method with params: $params", RPCError.NO_RESULTS))
          }
        case Some(err) => Failure(RPCException(s"An error occured when invoking RPC method: ${method.name} with params: $params", err))
      }
      // propagate the error to the caller
      case Failure(err) => Failure(err)
    }
  }
}