package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.CLPublicKey

/**
 * ValidatorWeight entity class
 *
 * @param public_key
 * @param weight
 */
case class ValidatorWeight(
                            public_key: CLPublicKey,
                            weight: String
                          )
