package com.casper.sdk.domain.deploy

import com.casper.sdk.types.cltypes.CLPublicKey

case class DeployHeader(
                         account: CLPublicKey,
                         timestamp: String,
                         ttl: String,
                         gas_price: Int,
                         body_hash: String,
                         dependencies: Seq[String],
                         chain_name: String
                       )

