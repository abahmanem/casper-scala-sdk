package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.URef
import com.casper.sdk.types.cltypes.CLType
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
                    bonding_purse: URef,
                    staked_amount: String,
                    delegation_rate: Int,
                    delegators: Seq[Delegator],
                    inactive: Boolean
                  )
