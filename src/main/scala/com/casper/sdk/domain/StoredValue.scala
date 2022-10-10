package com.casper.sdk.domain

import com.casper.sdk.domain.*
import com.casper.sdk.domain.deploy.*
import com.casper.sdk.types.cltypes.*

/**
 * StoredValue entity class
 * @param eraInfo
 * @param deployInfo
 * @param transfer
 * @param contractPackage
 * @param Contract
 * @param contractWASM
 * @param Account
 * @param CLValue
 */
case class StoredValue(
                    EraInfo : Option[EraInfo],
                    DeployInfo : Option[DeployInfo],
                    transfer: Option[BlockTransfer],
                    contractPackage : Option[ContractPackage],
                    Contract :  Option[Contract],
                    contractWASM :  Option[String],
                    Account :  Option[Account],
                    CLValue : Option[CLValue]
                 )
object StoredValue{
  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
  implicit val decoder:Decoder[StoredValue] = deriveDecoder[StoredValue]

}