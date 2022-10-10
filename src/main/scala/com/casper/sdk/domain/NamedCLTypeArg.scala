package com.casper.sdk.domain
import  com.casper.sdk.types.cltypes.CLType
import  com.casper.sdk.types.cltypes.CLValue

/**
 * NamedCLTypeArg entity class
 * @param name
 * @param cl_type
 */
case class NamedCLTypeArg(
                           name : String,
                           cl_type : CLType
                         )

object NamedCLTypeArg{

  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}


  implicit val decoder:Decoder[NamedCLTypeArg] = deriveDecoder[NamedCLTypeArg]
  implicit val encoder:Encoder[NamedCLTypeArg] = deriveEncoder[NamedCLTypeArg]
}
