package com.casper.sdk.core

case class Error(code: Int, message: String, data: Option[String] = None) {
  def fullMessage: String = message + data.map(": " + _).getOrElse("")
}

object Error {
  val default: Throwable = new Throwable("No result ")
}
