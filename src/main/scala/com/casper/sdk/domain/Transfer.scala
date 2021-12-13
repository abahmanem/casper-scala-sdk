package com.casper.sdk.domain

import com.casper.sdk.types.cltypes._


/**
 * Transfer entity class
 *
 * @param deploy_hash
 * @param from
 * @param to
 * @param source
 * @param target
 * @param amount
 * @param gas
 * @param id
 */
case class Transfer(
                     deploy_hash: String,
                     from: AccountHash,
                     to: AccountHash,
                     source: URef,
                     target: URef,
                     amount: BigInt,
                     gas: String,
                     id: BigInt
                   )
