package com.casper.sdk.core

import cats.Id
import com.casper.sdk.core.StatusAndBody
import com.casper.sdk.core.Request
import com.casper.sdk.core.JsonConverterByClass
import com.casper.sdk.core.IdTransport

import scala.reflect.Manifest
import java.net.URI
import java.net.http.{HttpClient, HttpRequest}
import java.time.Duration

import scala.reflect.ClassTag
import scala.reflect._

import scala.reflect.runtime.universe._

class HttpTransport(baseUrl: String, jsonConverter: JsonConverterByClass, connTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000)
  extends IdTransport{

  final val httpClient : HttpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(connTimeoutMs)) .build()

  override def send [T:ClassTag] (request: Request): Id[T] = {
    val response = post("", jsonConverter.toJson(request))
    try {
       parseResponse [T](response, request)
    } catch {
      case e: Throwable => throw new IllegalArgumentException(s"cannot parse json. http return code=${response.code} for request=$request", e)
    }
  }

  @throws[IllegalArgumentException]
  private def parseResponse[T : ClassTag](response: StatusAndBody, request: Request) : T = {
    try {
      jsonConverter.fromJson(response.body)
    } catch {
      case e: Throwable => throw new IllegalArgumentException(s"cannot parse json. http return code=${response.code} for request=$request", e)
    }
  }

  @throws[Exception]
  def post(url: String, request: String): StatusAndBody = try {
    val httpRequest : HttpRequest = HttpRequest.newBuilder(URI.create(baseUrl+url))
                                               .POST(HttpRequest.BodyPublishers.ofString(request))
                                               .timeout(Duration.ofSeconds(connTimeoutMs))
                                               .header("Content-Type", "application/json").build()
    import java.net.http.HttpResponse
    val future = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString)
    val body = future.get().body()
    val code = future.get().statusCode()
    StatusAndBody(code,body)
  } catch {
    case e: Throwable => throw new Exception(e)
  }

}
