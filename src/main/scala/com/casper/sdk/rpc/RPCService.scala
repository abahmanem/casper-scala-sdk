package com.casper.sdk.rpc

import scala.util.{Failure, Success, Try}
import com.casper.sdk.rpc.RPCRequest

import java.io.IOException
import scala.concurrent.Future
import scala.reflect.ClassTag
import io.circe.Decoder

/**
 * Trait RPCService to be used by RPCCommand to perform RPC calls, can be subclassed to use differents libraries :
 * RPCHTTPService : using OkHttp3
 * RPCWebClientService : using Spring Framework WebClient
 */
trait RPCService {

  /**
   * Performs a blocking JSON-RPC request
   *
   * @param request : request to perform
   * @tparam T : Casper type  item to be returned by the request
   * @return deserialized JSON-RPC response
   */
  def send[T: ClassTag](request: RPCRequest)(implicit decoder: Decoder[T]): Try[RPCResult[T]]

  /**
   * Performs an asynchronous JSON-RPC request
   *
   * @param request : request to perform
   * @tparam T : Casper type  item to be returned by the request
   * @return Future that will be completed when a result is returned or if the request  has failed
   */

  def sendAsync[T: ClassTag](request: RPCRequest)(implicit decoder: Decoder[T]): Future[Try[RPCResult[T]]]
}
