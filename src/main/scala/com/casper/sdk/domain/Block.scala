package com.casper.sdk.domain

import com.fasterxml.jackson.annotation.JsonProperty


case class Block(
                  hash: String,
                  header: BlockHeader,
                  body: BlockBody,
                  proofs: Seq[BlockProof]
                ) extends Serializable

case class BlockHeader(
                        parent_hash: String,
                        state_root_hash: String,
                        body_hash: String,
                        random_bit: Boolean,
                        accumulated_seed: String,
                        era_end: EraEnd,
                        timestamp: String,
                        era_id: BigInt,
                        height: BigInt,
                        protocol_version: String
                      )

case class BlockBody(
                      proposer: String,
                      deploy_hashes: Seq[String],
                      transfer_hashes: Seq[String]
                    )

case class EraEnd(
                   era_report: EraReport,
                   next_era_validator_weights: Seq[ValidatorWeight]
                 )

case class EraReport(
                      equivocators: Seq[String],
                      rewards: Seq[Reward],
                      inactive_validators: Seq[String]
                    )


case class ValidatorWeight(
                            validator: String,
                            weight: String
                          )

case class BlockProof(
                       public_key: String,
                       signature: String
                     )



case class Reward(
                   validator: String,
                   amount: BigInt
                 )