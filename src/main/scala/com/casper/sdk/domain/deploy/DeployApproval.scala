package com.casper.sdk.domain.deploy

import com.casper.sdk.types.cltypes.{CLPublicKey, Signature}
case class DeployApproval(
                      signer : Option[CLPublicKey] ,
                      signature : Option[Signature]
                    )

object DeployApproval{


  import io.circe.syntax._
  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
  implicit val decoder:Decoder[DeployApproval] = deriveDecoder[DeployApproval]
  implicit val encoder:Encoder[DeployApproval] = deriveEncoder[DeployApproval]

}