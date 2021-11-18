package com.casper.sdk.util

import cats.{Id, Monad}

import scala.annotation.tailrec

class IdInstance extends Monad[Id] {
  def throwError[X](e: Throwable): Id[X] = throw e

  override def map[X, Y](fa: Id[X])(f: X => Y): Id[Y] = f(fa)

  override def flatMap[X, Y](fa: Id[X])(f: X => Id[Y]): Id[Y] = f(fa)

  @tailrec override final def tailRecM[X, Y](a: X)(f: X => Either[X, Y]): Y = f(a) match {
    case Left(a1) => tailRecM(a1)(f)
    case Right(b) => b
  }

  override def pure[X](x: X): Id[X] = x
}
