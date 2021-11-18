package com.casper.sdk.rpc.exceptions

/**
 * Thrown by RPC Call when the connection to the RPC server failed before recieving a response.
 *
 * @param message
 * @param cause
 */
class RPCIOException(
                      cause: Throwable
                    ) extends RPCException(null, cause)

