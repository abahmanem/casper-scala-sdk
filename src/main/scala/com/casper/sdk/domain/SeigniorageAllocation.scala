package com.casper.sdk.domain

/**
 * SeigniorageAllocation entity class
 * @param Delegator
 * @param Validator
 * https://docs.rs/casper-types/1.4.4/casper_types/system/auction/enum.SeigniorageAllocation.html#
 */

case class SeigniorageAllocation(
                                  val Delegator: SeigniorageAllocationDelegator,
                                  val Validator: SeigniorageAllocationValidator
                                )






