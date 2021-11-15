package com.casper.sdk.rpc


case class RPCError(code: Int, message: String, data: Option[String] = None) {
  def fullStack: String = message + data.map(": " + _).getOrElse("")
}

object RPCError {
  val DEFAULT: RPCError = new RPCError(0, "No result returned by the RPC Call")
  val PARSE_ERROR: RPCError = new RPCError(-32700, "An error occurred on the server while parsing the JSON text.")
  val METHOD_NOT_FOUND: RPCError = new RPCError(-32601, "The method does not exist / is not available.")
  val INVALID_PARAMS: RPCError = new RPCError(-32602, "Invalid method parameter(s).")
}