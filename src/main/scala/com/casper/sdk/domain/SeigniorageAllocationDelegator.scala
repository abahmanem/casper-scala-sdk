package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.*

/**
 * SeigniorageAllocationDelegator entity class
 * @param delegator_public_key
 * @param validator_public_key
 * @param amount
 */
case class SeigniorageAllocationDelegator(
                                           val delegator_public_key: CLPublicKey,
                                           val validator_public_key: CLPublicKey,
                                           val amount: Int
                                         )


