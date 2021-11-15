package com.casper.sdk.rpc.exceptions

import com.casper.sdk.rpc.RPCError

class RPCException(val message: String, val cause: Throwable) extends Exception(message, cause)

class RPCCodeException(override val message: String, val error: RPCError) extends
  RPCException(s"$message... ${error.code}: ${error.fullStack}", null)
