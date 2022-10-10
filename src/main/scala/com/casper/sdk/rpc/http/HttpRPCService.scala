package com.casper.sdk.rpc.http

import com.casper.sdk.domain.Peer
import io.circe.Decoder
import io.circe.syntax._
import com.casper.sdk.rpc.exceptions.RPCException
import com.casper.sdk.rpc.http.ResponseCodeAndBody
import com.casper.sdk.rpc.{RPCError, RPCRequest, RPCResult, RPCService}
import com.casper.sdk.util.CirceConverter
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
  def send[T: ClassTag](request: RPCRequest)(implicit decoder: Decoder[T]): Try[RPCResult[T]] =
    CirceConverter.toJson[RPCRequest](request) match {
      case Success(x) =>
        post(x).map(p => p.body) match {
          case Success(y) => CirceConverter.convertToObj[RPCResult[T]](y)
          case Failure(err) => Failure(err)
        }
      case Failure(err) => Failure(err)
    }


  /**
   * Execute the POST request
   *
   * @param url
   * @param request
   * @throws RPCException
   * @return ResponseCodeAndBody
   */

  def post(request: String): Try[ResponseCodeAndBody] = Try {
    val response = httpClient.newCall(buildHttpRequest(request)).execute()
    ResponseCodeAndBody(response.code(), response.body().string())
  }

  /**
   * Perform asynchronous calls
   *
   * @param request : request to perform
   * @tparam T : Casper result type  item to be returned by the request
   * @return Future that will be completed when a result is returned or if a request  has failed
   */

  def sendAsync[T: ClassTag](request: RPCRequest)(implicit decoder: Decoder[T]): Future[Try[RPCResult[T]]] = {

    val future = new CompletableFuture[Try[RPCResult[T]]]
    CirceConverter.toJson[RPCRequest](request) match {
      case Success(x) => val response: Future[Response] = {
        val promise = Promise[Response]
        httpClient.newCall(buildHttpRequest(x)).enqueue(new Callback() {
          def onFailure(call: Call, e: IOException): Unit = promise.failure(e)

          def onResponse(call: Call, response: Response): Unit = promise.success(response)
        })
        promise.future
      }
        response onComplete {
          case Success(res) => Try {
            CirceConverter.convertToObj[RPCResult[T]](res.body.string)
          } match {
            case Success(x) => future.complete(x)
            case Failure(err) => future.complete(Failure(err))
          }
          case Failure(err) => future.complete(Failure(err))
        }
      case Failure(err) => future.complete(Failure(err))
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
