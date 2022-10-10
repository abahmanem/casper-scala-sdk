package com.casper.sdk.domain.deploy

import com.casper.sdk.domain.BlockTransfer
import com.casper.sdk.types.cltypes.{AccountHash, URef}

case class DeployInfo(
                       deploy_hash: String,
                       transfers : List[BlockTransfer],
                       from : Option[AccountHash],
                       source : Option[URef],
                       gas : String
                     )
object DeployInfo{

  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

  implicit val decoder:Decoder[DeployInfo] = deriveDecoder[DeployInfo]
  implicit val encoder:Encoder[DeployInfo] = deriveEncoder[DeployInfo]
}

