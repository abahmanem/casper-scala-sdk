package com.casper.sdk.domain

/**
 * EraInfa entity class
 * @param seigniorage_allocations
 */
case class EraInfo(
                    val seigniorage_allocations : Set[SeigniorageAllocation]
                  )
