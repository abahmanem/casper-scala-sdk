package com.casper.sdk.rpc

import com.casper.sdk.rpc.RPCRequest
import com.casper.sdk.rpc.result.RPCResult

import java.io.IOException
import scala.concurrent.Future
import scala.reflect.ClassTag

/**
 *
 */
trait RPCService {

  /**
   * Perform a synchronous JSON-RPC request
   *
   * @param request : request to perform
   * @tparam T : Casper type  item to be returned by the request
   * @return deserialized JSON-RPC response
   */
  @throws[IOException]
  def send[T: ClassTag](request: RPCRequest): RPCResult[T]

  /**
   * Perform a synchronous JSON-RPC request
   *
   * @param request : request to perform
   * @tparam T : Casper type  item to be returned by the request
   * @return Future that will be completed when a result is returned or if the request  has failed
   */
  @throws[IOException]
  def sendAsync[T: ClassTag](request: RPCRequest): Future[RPCResult[T]]
}
