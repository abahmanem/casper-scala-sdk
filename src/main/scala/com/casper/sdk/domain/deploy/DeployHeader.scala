package com.casper.sdk.domain.deploy

import com.casper.sdk.crypto.hash.Hash
import com.casper.sdk.types.cltypes.CLPublicKey

/**
 * DeployHeader Entity class
 * @param account
 * @param timestamp
 * @param ttl
 * @param gas_price
 * @param body_hash
 * @param dependencies
 * @param chain_name
 */
case class DeployHeader(
                         account: Option[CLPublicKey],
                         timestamp: String,
                         ttl: String,
                         gas_price: Int,
                         body_hash: Option[Hash],
                         var dependencies: Seq[Hash],
                         chain_name: String
                       )

object DeployHeader {
  import io.circe.syntax._
  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

  implicit val decoder: Decoder[DeployHeader] = deriveDecoder[DeployHeader]
  implicit val encoder:Encoder[Deploy] = deriveEncoder[Deploy]
}