package com.casper.sdk.domain

/**
 * EraValidator entity class
 *
 * @param era_id
 * @param validator_weights
 */

case class EraValidator(
                         era_id: Int,
                         validator_weights: Seq[ValidatorWeight]
                       )
