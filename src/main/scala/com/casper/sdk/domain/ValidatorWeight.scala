package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.CLPublicKey
import io.circe.generic.semiauto.deriveEncoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.{Decoder, Encoder}

/**
 * ValidatorWeight entity class
 *
 * @param public_key
 * @param weight
 */
case class ValidatorWeight(
                            public_key: Option[CLPublicKey],
                            weight: String
                          )

object ValidatorWeight {

  implicit val decoder: Decoder[ValidatorWeight] = deriveDecoder[ValidatorWeight]
  implicit val encoder: Encoder[ValidatorWeight] = deriveEncoder[ValidatorWeight]
}