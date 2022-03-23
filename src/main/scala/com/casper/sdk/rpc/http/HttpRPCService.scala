package com.casper.sdk.rpc.http

import com.casper.sdk.rpc.exceptions.{RPCException, RPCIOException}
import com.casper.sdk.rpc.http.ResponseCodeAndBody
import com.casper.sdk.rpc.{RPCError, RPCRequest, RPCResult, RPCService}
import com.casper.sdk.util.JsonConverter
import com.fasterxml.jackson.databind.node.ObjectNode
import okhttp3._

import java.io.IOException
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.util.concurrent.CompletableFuture
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future, Promise, duration}
import scala.jdk.FutureConverters._
import scala.reflect.ClassTag
import scala.util.{Failure, Success, Try}

/**
 * HttpRPCService class
 *
 * @param url
 * @param httpClient
 */

class HttpRPCService(var url: String, var httpClient: OkHttpClient) extends RPCService {

  /**
   * Constructor with URL parameter
   *
   * @param url
   */
  def this(url: String) = this(url, HttpRPCService.HTTP_DEFAULT_CLIENT)

  /**
   * constructor with an  OkHttpClient paramater
   *
   * @param httpClient
   */
  def this(httpClient: OkHttpClient) = this(HttpRPCService.DEFAULT_URL, httpClient)

  /**
   * Perform  blocking calls
   *
   * @param request : request to perform
   * @tparam T : Casper result type  item to be returned by the request
   * @return :Deserialized JSON-RPC response
   */
  def send[T: ClassTag](request: RPCRequest): Option[RPCResult[T]] =

    Try {
      val response = post(JsonConverter.toJson(request).get).get
      val typedJsonBody = response.body.patch(1, "\"rpc_call\":\"" + request.method + "\",", 0)
      JsonConverter.fromJson[RPCResult[T]](typedJsonBody)
    } match {
      case Success(x) => x
      case Failure(err) => {
        Some(new RPCResult(RPCError(1, err.getMessage)))
      }
    }

  /**
   * Execute the POST request
   *
   * @param url
   * @param request
   * @throws RPCException
   * @return ResponseCodeAndBody
   */

  def post(request: String): Try[ResponseCodeAndBody] =
    try {
      val response = httpClient.newCall(buildHttpRequest(request)).execute()
      Success(ResponseCodeAndBody(response.code(), response.body().string()))
    } catch {
      case e: Throwable => Failure(e)
    }

  /**
   * Perform asynchronous calls
   *
   * @param request : request to perform
   * @tparam T : Casper result type  item to be returned by the request
   * @return Future that will be completed when a result is returned or if a request  has failed
   */

  def sendAsync[T: ClassTag](request: RPCRequest): Future[Option[RPCResult[T]]] = {
    val response: Future[Response] = {
      val promise = Promise[Response]
      httpClient.newCall(buildHttpRequest(JsonConverter.toJson(request).get)).enqueue(new Callback() {
        def onFailure(call: Call, e: IOException): Unit = promise.failure(e)

        def onResponse(call: Call, response: Response): Unit = promise.success(response)
      })
      promise.future
    }

    var future = new CompletableFuture[Option[RPCResult[T]]]
    response onComplete {
      case Success(res) => {
        Try {
          val typedJsonBody = res.body.string.patch(1, "\"rpc_call\":\"" + request.method + "\",", 0)
          JsonConverter.fromJson[RPCResult[T]](typedJsonBody)
        } match {
          case Success(x) => future.complete(x)
          case Failure(err) => future.complete(Some(new RPCResult(RPCError(0, err.getMessage))))
        }
      }
      case Failure(e) => future.complete(Some(new RPCResult(RPCError(0, e.getMessage))))
    }
    future.asScala
  }

  /**
   * Build  a OkHttp Request instance from serialzed RCPRequest
   *
   * @param request
   * @return
   */
  private def buildHttpRequest(request: String): okhttp3.Request = {
    val JSON: MediaType = HttpRPCService.JSON_MEDIA_TYPE
    val bytes = request.getBytes(StandardCharsets.UTF_8)
    val body = RequestBody.create(bytes, JSON)
    new Request.Builder().url(url).post(body).build()
  }
}

/**
 * Companion object
 */
object HttpRPCService {
  final val DEFAULT_URL = "http://localhost:7777/rpc"
  final val DEFAULT_TIMEOUT_SEC: Int = 10
  final val JSON_MEDIA_TYPE: MediaType = MediaType.Companion.get("application/json")
  final val HTTP_DEFAULT_CLIENT = OkHttpClient.Builder().connectTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT_SEC)).build()
}
