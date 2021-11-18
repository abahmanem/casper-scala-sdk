package com.casper.sdk.rpc.result

import com.casper.sdk.domain.AuctionState

case class AuctionStateResult(api_version: String,
                              auction_state: AuctionState
                             ) 
