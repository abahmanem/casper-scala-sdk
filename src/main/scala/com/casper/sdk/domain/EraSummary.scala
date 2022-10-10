package com.casper.sdk.domain

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

/**
 * EraSummary entity class
 *
 * @param block_hash
 * @param era_id
 * @param stored_value
 * @param state_root_hash
 * @param merkle_proof
 */
case class EraSummary(
                        block_hash: String,
                        era_id: Int,
                        stored_value: StoredValue,
                        state_root_hash: String,
                        merkle_proof: String
                     )

object EraSummary{

  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

  implicit val decoder:Decoder[EraSummary] = deriveDecoder[EraSummary]
 }