package com.casper.sdk.rpc.result
import com.casper.sdk.domain.Block

/**
 * getBlock RPC response
 * @param api_version
 * @param block
 */
case class BlockResult(api_version:String, block:Block)


object BlockResult{
  import io.circe.Decoder
  import io.circe.generic.semiauto.deriveDecoder
  implicit val decoder:Decoder[BlockResult] = deriveDecoder[BlockResult]
 
}
