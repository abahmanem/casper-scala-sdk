package com.casper.sdk.domain
import io.circe.generic.semiauto.deriveEncoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.{Decoder, Encoder}
import com.casper.sdk.types.cltypes.CLPublicKey


/**
 * Reward entity class
 *
 * @param validator
 * @param amount
 */

case class Reward(
                   //validator: CLPublicKey,
                   validator:String,
                   amount: Long
                 )
object Reward{
  
  implicit val decoder:Decoder[Reward] = deriveDecoder[Reward]
  implicit val encoder:Encoder[Reward] = deriveEncoder[Reward]
}
