package com.casper.sdk.domain

/**
 * BlockHeader entity class
 * @param parent_hash
 * @param state_root_hash
 * @param body_hash
 * @param random_bit
 * @param accumulated_seed
 * @param era_end
 * @param timestamp
 * @param era_id
 * @param height
 * @param protocol_version
 */
case class BlockHeader(
                        parent_hash: String,
                        state_root_hash: String,
                        body_hash: String,
                        random_bit: Boolean,
                        accumulated_seed: String,
                        era_end: EraEnd,
                        timestamp: String,
                        era_id: Int,
                        height: Int,
                        protocol_version: String
                      )
