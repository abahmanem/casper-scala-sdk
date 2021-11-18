package com.casper.sdk.domain

/**
 * EraEnd entity class
 *
 * @param era_report
 * @param next_era_validator_weights
 */
case class EraEnd(
                   era_report: EraReport,
                   next_era_validator_weights: Seq[ValidatorWeight]
                 )

