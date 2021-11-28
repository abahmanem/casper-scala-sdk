package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.CLPublicKey

case class SeigniorageAllocationValidator(
                                           val validator_public_key : CLPublicKey,
                                           val amount : Int
                                         )

