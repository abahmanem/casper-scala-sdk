package com.casper.sdk.domain

/**
 * Delegator entity class
 *
 * @param public_key
 * @param staked_amount
 * @param bonding_purse
 * @param delegatee
 */
case class Delegator(
                      public_key: String,
                      staked_amount: BigInt,
                      bonding_purse: String, //Uref
                      delegatee: String
                    )
