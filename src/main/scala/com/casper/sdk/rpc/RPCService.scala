package com.casper.sdk.rpc

import com.casper.sdk.rpc.RPCRequest
import com.casper.sdk.rpc.result.RPCResult

import java.io.IOException
import scala.concurrent.Future
import scala.reflect.ClassTag

/**
 * Trait RPCService to be used by RPCCommand to perform RPC calls, can be subclassed to use differents libraries :
 * RPCHTTPService : using OkHttp3
 * RPCWebClientService : using Spring Framework WebClient
 */
trait RPCService {

  /**
   * Performs a synchronous JSON-RPC request
   *
   * @param request : request to perform
   * @tparam T : Casper type  item to be returned by the request
   * @return deserialized JSON-RPC response
   */
  @throws[IOException]
  def send[T: ClassTag](request: RPCRequest): RPCResult[T]

  /**
   * Performs a synchronous JSON-RPC request
   *
   * @param request : request to perform
   * @tparam T : Casper type  item to be returned by the request
   * @return Future that will be completed when a result is returned or if the request  has failed
   */
  @throws[IOException]
  def sendAsync[T: ClassTag](request: RPCRequest): Future[RPCResult[T]]
}
