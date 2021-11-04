package com.casper.sdk.result

import  com.casper.sdk.core.Error

case class PeerResult
(
  node_id: String,
  address: String
)

case class PeersMapResult
(
  api_version : String,
  peers : List[PeerResult]
)


case class GetPeersResult
(
  jsonrpc: String,
  id: Long,
  result: Option[PeersMapResult],
  error: Option[Error] = None,
) extends RPCResult(jsonrpc, id, result, error)
