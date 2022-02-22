package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.CLPublicKey

/**
 * NodeStatus entity class
 *
 * @param api_version
 * @param chainspec_name
 * @param starting_state_root_hash
 * @param peers
 * @param last_added_block_info
 * @param our_public_signing_key
 * @param round_length
 * @param next_upgrade
 * @param build_version
 * @param uptime
 */
case class NodeStatus(
                       // api_version: String,
                       chainspec_name: String,
                       starting_state_root_hash: String,
                       peers: Seq[Peer],
                       last_added_block_info: BlockInfo,
                       our_public_signing_key: Option[CLPublicKey],
                       round_length: String,
                       next_upgrade: NextUpgrade,
                       build_version: String,
                       uptime: String
                     )
