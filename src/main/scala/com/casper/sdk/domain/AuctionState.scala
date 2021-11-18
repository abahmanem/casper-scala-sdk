package com.casper.sdk.domain

/**
 * AuctionState entity class
 * @param state_root_hash
 * @param block_height
 * @param era_validators
 * @param bids
 */
case class AuctionState(
                         state_root_hash: String,
                         block_height: String,
                         era_validators: Seq[EraValidator],
                         bids: Seq[Bid]
                       )
