package com.casper.sdk.domain

/**
 * Bid entity class
 *
 * @param public_key
 * @param bid
 */

case class Bid(
                public_key: String, //TODO replace with CLPubKey
                bid: BidInfo
              )
