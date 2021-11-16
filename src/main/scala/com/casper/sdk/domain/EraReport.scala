package com.casper.sdk.domain

case class EraReport1 (
                       equivocators: Seq[String],
                       rewards: Seq[Reward1],
                       inactive_validators: Seq[String]
                     )
