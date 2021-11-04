package com.casper.sdk.core

import cats.Monad

import scala.language.higherKinds

trait MonadException[F[_]] extends Monad[F] {
  def raiseError[A](e: Throwable): F[A]

}
