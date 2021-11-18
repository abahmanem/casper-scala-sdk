package com.casper.sdk.domain

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Block entity class
 * @param hash
 * @param header
 * @param body
 * @param proofs
 */

case class Block(
                  hash: String,
                  header: BlockHeader,
                  body: BlockBody,
                  proofs: Seq[BlockProof]
                ) 

