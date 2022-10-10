package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.CLType

/**
 * EntryPoint entity class
 * @param name
 * @param entry_point_type
 * @param access
 * @param ret
 * @param args
 */
case class EntryPoint(
                      name:String,
                      entry_point_type : String,
                      access :String,
                      ret: CLType,
                      args: Seq[NamedCLTypeArg]
                     )
object EntryPoint{

  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}


  implicit val decoder:Decoder[EntryPoint] = deriveDecoder[EntryPoint]
  implicit val encoder:Encoder[EntryPoint] = deriveEncoder[EntryPoint]
}
