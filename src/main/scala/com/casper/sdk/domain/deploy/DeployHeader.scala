package com.casper.sdk.domain.deploy

import com.casper.sdk.types.cltypes.CLPublicKey

/**
 * DeployHeader Entity class
 * @param account
 * @param timestamp
 * @param ttl
 * @param gas_price
 * @param body_hash
 * @param dependencies
 * @param chain_name
 */
case class DeployHeader(
                         account: CLPublicKey,
                         timestamp: String,
                         ttl: String,
                         gas_price: Int,
                         body_hash: String,
                         dependencies: Seq[String],
                         chain_name: String
                       )

