package com.casper.sdk.domain


case class EraEnd1 (
                    era_report: EraReport,
                    next_era_validator_weights: Seq[ValidatorWeight]
                  )