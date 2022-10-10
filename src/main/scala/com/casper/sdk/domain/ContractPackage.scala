package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.URef

/**
 * ContractPackage entity class
 * @param accessKey
 * @param contractVersions
 * @param disabledVersions
 * @param groups
 */
case class ContractPackage(
                            accessKey : Option[URef],
                            contractVersions : Seq[String],
                            disabledVersions : Seq[String],
                            groups:Seq[Group]
                          )

object ContractPackage{

  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

  implicit val decoder:Decoder[ContractPackage] = deriveDecoder[ContractPackage]
  implicit val encoder:Encoder[ContractPackage] = deriveEncoder[ContractPackage]
}

