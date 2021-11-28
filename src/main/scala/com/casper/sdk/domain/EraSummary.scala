package com.casper.sdk.domain

case class EraSummary(
                       val block_hash: String,
                       val era_id: Int,
                       val stored_value: StoredValue,
                       val state_root_hash: String,
                       val merkle_proof: String
                     )

