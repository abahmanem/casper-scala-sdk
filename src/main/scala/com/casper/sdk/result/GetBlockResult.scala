package com.casper.sdk.result
import  com.casper.sdk.core.Error
import com.casper.sdk.result.RPCResult
import com.casper.sdk.result.HeaderResult
import com.casper.sdk.result.ProofResult

case class GetBlockResult
(
  jsonrpc: String,
  id: Long,
  result: Option[BlockResult],
  error: Option[Error] = None,
) extends RPCResult(jsonrpc, id, result, error)


case class BlockBodyResult(
  proposer : String,
  deploy_hashes : List[String],
  transfer_hashes : List[String]
)

case class BlockMapResult
(
  hash: String,
  header: HeaderResult,
  body : BlockBodyResult,
  proofs : List[ProofResult]
)

case class BlockResult(
  api_version : String,
  block : BlockMapResult
)
