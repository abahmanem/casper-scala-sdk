package com.casper.sdk.domain


case class EraValidator(era_id: Int,
                        validator_weights: Seq[ValidatorWeight]
                       )
