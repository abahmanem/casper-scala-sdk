package com.casper.sdk.domain.deploy

import io.circe.syntax._
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import com.casper.sdk.types.cltypes.{CLPublicKey, Signature}
case class DeployApproval(
                      signer : Option[CLPublicKey] ,
                      signature : Option[Signature]
                    )

object DeployApproval{
  implicit val decoder:Decoder[DeployApproval] = deriveDecoder[DeployApproval]
  implicit val encoder:Encoder[DeployApproval] = deriveEncoder[DeployApproval]
}