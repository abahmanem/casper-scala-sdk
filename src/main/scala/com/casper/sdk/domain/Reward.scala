package com.casper.sdk.domain

/**
 * Reward entity class
 *
 * @param validator
 * @param amount
 */

case class Reward(
                   validator: String,
                   amount: Int
                 )