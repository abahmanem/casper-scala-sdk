package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.{CLPublicKey, URef}

/**
 * Delegator entity class
 *
 * @param public_key
 * @param staked_amount
 * @param bonding_purse
 * @param delegatee
 */
case class Delegator(
                      public_key: Option[CLPublicKey],
                      staked_amount: BigInt,
                      bonding_purse: URef,
                      delegatee: String
                    )
