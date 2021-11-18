package com.casper.sdk.domain

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
                     from: String, //TODO repalce by CLAccountHash
                     to: String, //TODO repalce by CLAccountHash
                     source: String, //TODO repalce by CLUREF
                     target: String, //TODO repalce by CLUREF
                     amount: BigInt,
                     gas: String,
                     id: BigInt
                   )
