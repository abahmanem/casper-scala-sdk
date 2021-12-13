package com.casper.sdk.domain

/**
 * EraSummary entity class
 * @param block_hash
 * @param era_id
 * @param stored_value
 * @param state_root_hash
 * @param merkle_proof
 */
case class EraSummary(
                       val block_hash: String,
                       val era_id: Int,
                       val stored_value: StoredValue,
                       val state_root_hash: String,
                       val merkle_proof: String
                     )

