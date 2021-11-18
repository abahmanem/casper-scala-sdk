package com.casper.sdk.domain

/**
 * BlockBody entity class
 * @param proposer
 * @param deploy_hashes
 * @param transfer_hashes
 */
case class BlockBody(
                      proposer: String,
                      deploy_hashes: Seq[String],
                      transfer_hashes: Seq[String]
                    )

