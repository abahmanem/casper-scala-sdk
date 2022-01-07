package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.CLPublicKey

/**
 * Reward entity class
 *
 * @param validator
 * @param amount
 */

case class Reward(
                   validator: CLPublicKey,
                   amount: Int
                 )