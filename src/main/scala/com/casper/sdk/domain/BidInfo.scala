package com.casper.sdk.domain

/**
 * BidInfo entity class
 *
 * @param bonding_purse
 * @param staked_amount
 * @param delegation_rate
 * @param delegators
 * @param inactive
 */
case class BidInfo(
                    bonding_purse: String,
                    staked_amount: String,
                    delegation_rate: Int,
                    delegators: Seq[Delegator],
                    inactive: Boolean
                  )
