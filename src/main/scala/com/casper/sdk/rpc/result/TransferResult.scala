package com.casper.sdk.rpc.result
import com.casper.sdk.domain.BlockTransfer

/**
 * getBlockTransfers RPC response
 * @param api_version
 * @param block_hash
 * @param transfers
 */
case class TransferResult(api_version: String, block_hash:String, transfers: List[BlockTransfer])

object TransferResult{
  import io.circe.syntax._
  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
  implicit val decoder:Decoder[TransferResult] = deriveDecoder[TransferResult]
 }

