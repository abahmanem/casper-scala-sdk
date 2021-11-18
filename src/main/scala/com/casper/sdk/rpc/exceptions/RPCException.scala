package com.casper.sdk.rpc.exceptions

import com.casper.sdk.rpc.RPCError


/**
 * RPC Exception Class
 * @param message
 * @param cause
 */
class RPCException( message: String,  cause: Throwable
                  ) extends Exception(message, cause)


object RPCException {
  def apply(message: String, error: RPCError): RPCException = new RPCException(s"$message. RPC error code: ${error.code} , RPC error message: ${error.fullStack}",null)
}
