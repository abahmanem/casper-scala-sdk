package com.casper.sdk.core
object implicits {
  //implicit lazy val tryInstance: TryInstance = new TryInstance
  implicit lazy val idInstance: IdException = new IdException
}
