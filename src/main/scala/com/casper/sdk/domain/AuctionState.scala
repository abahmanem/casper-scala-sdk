package com.casper.sdk.domain


case class AuctionState(
                         state_root_hash: String,
                         block_height: String,
                         era_validators: List[EraValidator],
                         bids: List[Bid]
                       )
