package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.CLPublicKey

/**
 * SeigniorageAllocationValidator entity class
 * @param validator_public_key
 * @param amount
 */
case class SeigniorageAllocationValidator(
                                           val validator_public_key : CLPublicKey,
                                           val amount : Int
                                         )

