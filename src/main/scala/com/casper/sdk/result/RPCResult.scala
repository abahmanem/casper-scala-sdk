package com.casper.sdk.result
import  com.casper.sdk.core.Error
import scala.reflect.ClassTag
import scala.reflect._

class RPCResult[T : ClassTag]
(
  jsonrpc: String,
  id: Long,
  result: Option[T],
  error: Option[Error] = None,
)
