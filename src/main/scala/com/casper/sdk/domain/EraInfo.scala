package com.casper.sdk.domain

/**
 * EraInfa entity class
 * @param seigniorage_allocations
 */
case class EraInfo(
                     seigniorage_allocations : Set[SeigniorageAllocation]
                  )

object EraInfo{

  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

  implicit val decoder:Decoder[EraInfo] = deriveDecoder[EraInfo]
 }
