package com.casper.sdk.core

import java.util.concurrent.atomic.AtomicInteger
import scala.language.higherKinds
import scala.reflect.Manifest


import cats.syntax.flatMap.toFlatMapOps
import cats.Monad

import com.casper.sdk.core.Transport
import com.casper.sdk.core.MonadException
import com.casper.sdk.core.Transport
import com.casper.sdk.core.Request
import com.casper.sdk.core.Error
import com.casper.sdk.core.JsonConverterByClass

import scala.reflect.runtime.universe._
import scala.reflect.ClassTag
import scala.reflect._
/**
 * Client RPC
 * @param transport
 * @param ex
 * @tparam F
 */
class Client[F[_]](transport: Transport[F]) (implicit ex: MonadException[F]) {
  def call[T : ClassTag](method: String, params: Seq[Any]): F[T] = callOption[T](method, params)
  def callOption[T : ClassTag](method: String,   params: Seq[Any]): F[T] = transport.send[T](Request(Client.id.incrementAndGet(), method, params))
}
object Client {
  val id = new AtomicInteger()
}
