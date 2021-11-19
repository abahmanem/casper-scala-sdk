package com.casper.sdk.rpc

/**
 * Class that will be used to serialize/deserialize RPC Errors
 * @param code : RPC Error code
 * @param message : RPC Error short message
 * @param data   : Full stack RPC error  message
 */

case class RPCError(code: Int, message: String, data: Option[String] = None) {
  def fullStack: String = message + data.map(" data : " + _).getOrElse("")
}

object RPCError {
  val NO_RESULTS: RPCError = new RPCError(0, "No result returned by the RPC Call")
  val PARSE_ERROR: RPCError = new RPCError(-32700, "An error occurred on the server while parsing the JSON text.")
  val METHOD_NOT_FOUND: RPCError = new RPCError(-32601, "The method does not exist / is not available.")
  val INVALID_PARAMS: RPCError = new RPCError(-32602, "Invalid method parameter(s).")
}