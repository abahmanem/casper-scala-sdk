package com.casper.sdk.rpc.http

import com.casper.sdk.rpc.result.RPCResult
import com.casper.sdk.rpc.http.ResponseCodeAndBody
import com.casper.sdk.rpc.RPCRequest

import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.time.Duration
import com.casper.sdk.rpc.{RPCRequest, RPCService}
import com.casper.sdk.util.JsonConverter

import scala.concurrent.Future
import scala.reflect.ClassTag
import scala.concurrent.ExecutionContext.Implicits.global
import okhttp3.{Call, Callback, MediaType, OkHttpClient, Request, RequestBody, Response}

import java.io.IOException
import java.nio.charset.StandardCharsets
import scala.concurrent.Future
import scala.concurrent.duration.*


class HttpRPCService(var url: String, var httpClient: OkHttpClient) extends RPCService {

  /**
   * Construcotr with URL parameter
   *
   * @param url
   */
  def this(url: String) = this(url, HttpRPCService.HTTP_DEFAULT_CLIENT)

  /**
   * constructor with a  OkHttpClient paramater
   *
   * @param httpClient
   */
  def this(httpClient: OkHttpClient) = this(HttpRPCService.DEFAULT_URL, httpClient)

  /**
   *
   * @param request : request to perform
   * @tparam T : Casper type  item to be returned by the request
   * @return deserialized JSON-RPC response
   */
  def send[T: ClassTag](request: RPCRequest): RPCResult[T] = {
    val response = post("", JsonConverter.toJson(request))
    try {
      //Get ClassTag name
      val tpe = implicitly[ClassTag[T]].runtimeClass.asInstanceOf[Class[T]].getName
      parseResponse[RPCResult[T]](response, request, tpe)
    } catch {
      case e: Throwable => throw new IllegalArgumentException(s"cannot parse json. http return code=${response.code} for request=$request", e)
    }
  }

  /**
   *
   * @param request : request to perform
   * @tparam T : Casper type  item to be returned by the request
   * @return Future that will be completed when a result is returned or if a request  has failed
   */
  def sendAsync[T: ClassTag](request: RPCRequest): Future[RPCResult[T]] = Future {
    send(request)
  }

  /**
   * Build  a OkHttp Request instance from serialzed RCPRequest
   *
   * @param request
   * @return
   */
  private def buildHttpRequest(request: String): okhttp3.Request = {
    //.addHeader("Content-Type", "application/json")
    //.addHeader("Accept", "application/json") .url(url). post( body ).build()
    val json: MediaType = HttpRPCService.JSON_MEDIA_TYPE
    val bytes = request.getBytes(StandardCharsets.UTF_8)
    val body = RequestBody.create(bytes, json)
    val httpRequest: Request = new Request.Builder().url(url).post(body).build()
    httpRequest
  }

  /**
   * Execute the POST request
   *
   * @param url
   * @param request
   * @throws
   * @return ResponseCodeAndBody
   */
  @throws[Exception]
  def post(url: String, request: String): ResponseCodeAndBody = try {
    val response = httpClient.newCall(buildHttpRequest(request)).execute()
    ResponseCodeAndBody(response.code(), response.body().string())
  } catch {
    case e: Throwable => throw new Exception(e)
  }

  /**
   * Parse response paylaodd into RPCResult
   *
   * @param response
   * @param request
   * @param tpe : Casper type
   * @tparam T Casper type
   * @throws IllegalArgumentException
   * @return RPCResult[T]
   */

  @throws[IllegalArgumentException]
  private def parseResponse[T: ClassTag](response: ResponseCodeAndBody, request: RPCRequest, tpe: String): T = {
    try {
      //We have to inject type attribute in json response needed for the deserialization of RPCRESULT subtypes
      val typedJsonBody = response.body.patch(1, "\"type\":\"" + tpe + "\",", 0)
      JsonConverter.fromJson(typedJsonBody)

    } catch {
      case e: Throwable => throw new IllegalArgumentException(s"An error occurred while parsing the JSON. Http return code=${response.code} for request=$request", e)
    }
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

