package com.casper.sdk.domain

import com.casper.sdk.types.cltypes._


/**
 * Transfer entity class
 *
 * @param deploy_hash
 * @param from
 * @param to
 * @param source
 * @param target
 * @param amount
 * @param gas
 * @param id
 */
case class BlockTransfer(
                     deploy_hash: String,
                     from: Option[AccountHash],
                     to: Option[AccountHash],
                     source: Option[URef],
                     target: Option[URef],
                     amount: BigInt,
                     gas: String,
                     id: Option[BigInt]
                   )

object BlockTransfer{
  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.deriveEncoder
  import io.circe.generic.semiauto.deriveDecoder

  implicit val decoder:Decoder[BlockTransfer] = deriveDecoder[BlockTransfer]
  implicit val encoder:Encoder[BlockTransfer] = deriveEncoder[BlockTransfer]
}
