package com.casper.sdk.domain

/**
 * EraReport entity class
 *
 * @param equivocators
 * @param rewards
 * @param inactive_validators
 */
case class EraReport(
                      equivocators: Seq[String],
                      rewards: Seq[Reward],
                      inactive_validators: Seq[String]
                    )
