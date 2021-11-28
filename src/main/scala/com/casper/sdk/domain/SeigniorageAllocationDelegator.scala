package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.*

case class SeigniorageAllocationDelegator(
                                           val delegator_public_key: CLPublicKey,
                                           val validator_public_key: CLPublicKey,
                                           val amount: Int
                                         )


