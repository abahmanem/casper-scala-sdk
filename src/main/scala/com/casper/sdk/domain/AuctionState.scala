package com.casper.sdk.domain


case class AuctionState(
                         state_root_hash: String,
                         block_height: String,
                         era_validators: Seq[EraValidator],
                         bids: Seq[Bid]
                       )
