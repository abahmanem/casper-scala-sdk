package com.casper.sdk.rpc.http

import com.casper.sdk.rpc.exceptions.{RPCException, RPCIOException}
import com.casper.sdk.rpc.http.ResponseCodeAndBody
import com.casper.sdk.rpc.result.RPCResult
import com.casper.sdk.rpc.{RPCRequest, RPCService}
import com.casper.sdk.util.JsonConverter
import okhttp3.*

import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.nio.charset.StandardCharsets
import java.time.Duration
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.*
import scala.reflect.ClassTag

/**
 *
 * @param url
 * @param httpClient
 */

class HttpRPCService(var url: String, var httpClient: OkHttpClient) extends RPCService {

  /**
   * Construcotr with URL parameter
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
   * Perform asynchronous calls
   * @param request : request to perform
   * @tparam T : Casper result type  item to be returned by the request
   * @return Future that will be completed when a result is returned or if a request  has failed
   */
  def sendAsync[T: ClassTag](request: RPCRequest): Future[RPCResult[T]] = Future {
    send(request)
  }

  /**
   * Perform non blocking calls
   * @param request : request to perform
   * @tparam T : Casper result type  item to be returned by the request
   * @return :Deserialized JSON-RPC response
   */
  def send[T: ClassTag](request: RPCRequest): RPCResult[T] = {
    val response = post(JsonConverter.toJson(request))
    try {
      //We add type attribute in json response. It is needed for the deserialization of RPCRESULT subtypes
      val tpe = implicitly[ClassTag[T]].runtimeClass.asInstanceOf[Class[T]].getName
      val typedJsonBody = response.body.patch(1, "\"type\":\"" + tpe + "\",", 0)
      JsonConverter.fromJson[RPCResult[T]](typedJsonBody)

    } catch {
      case e: Throwable => throw new IllegalArgumentException(s"cannot parse json. http return code=${response.code} for request=$request", e)
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
  @throws[RPCIOException]
  def post(request: String): ResponseCodeAndBody = try {

    val response = httpClient.newCall(buildHttpRequest(request)).execute()
    ResponseCodeAndBody(response.code(), response.body().string())
  } catch {
    case e: Throwable => throw new RPCIOException(e)
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
    val httpRequest: Request = new Request.Builder().url(url).post(body).build()
    httpRequest
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

