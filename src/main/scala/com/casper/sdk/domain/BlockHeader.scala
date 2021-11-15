package com.casper.sdk.domain

case class BlockHeader(
                        parent_hash: String,
                        state_root_hash: String,
                        body_hash: String,
                        random_bit: Boolean,
                        accumulated_seed: String,
                        era_end: String,
                        timestamp: Int,
                        era_id: Int,
                        height: Int,
                        protocol_version: String
                      )
