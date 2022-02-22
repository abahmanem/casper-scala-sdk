package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.CLPublicKey

/**
 * Bid entity class : https://docs.rs/casper-types/1.4.4/casper_types/system/auction/struct.Bid.html
 *
 * @param public_key
 * @param bid
 */

case class Bid(
                public_key: Option[CLPublicKey],
                bid: BidInfo,
               )
