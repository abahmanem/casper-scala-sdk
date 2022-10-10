package com.casper.sdk.rpc.result
import com.casper.sdk.domain.AuctionState

case class AuctionStateResult(api_version:String, auction_state:AuctionState)

object AuctionStateResult{
  import io.circe.Decoder
  import io.circe.generic.semiauto.deriveDecoder
  implicit val decoder:Decoder[AuctionStateResult] = deriveDecoder[AuctionStateResult]
 
}
