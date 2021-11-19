package com.casper.sdk.domain

/**
 * BlockProof entity class
 *
 * @param public_key
 * @param signature
 */
case class BlockProof(
                       public_key: String, //TODO replace by CLPublicKey
                       signature: String
                     )
