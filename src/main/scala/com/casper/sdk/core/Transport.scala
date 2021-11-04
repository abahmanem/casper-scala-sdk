package com.casper.sdk.core

import com.casper.sdk.core.Request

import scala.reflect.runtime.universe._
import scala.reflect.ClassTag
import scala.reflect._

trait Transport[F[_]] {
  def send[T : ClassTag](request: Request): F[T]
}
