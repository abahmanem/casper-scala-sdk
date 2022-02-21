package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.CLPublicKey

/**
 * BlockProof entity class
 *
 * @param public_key
 * @param signature
 */
case class BlockProof(
                       public_key: Option[CLPublicKey],
                       signature: String
                     )
