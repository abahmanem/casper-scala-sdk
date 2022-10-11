package com.casper.sdk.types.cltypes

/**
 * Uref Access rights Enum
 */
enum AccessRight(val bits: Byte) extends App {
  /* No permissions 0b00*/
  case ACCESS_NONE extends AccessRight(0)
  /* Permission to read the value under the associated [[URef]] 0b001*/
  case ACCESS_READ extends AccessRight(1)
  /* Permission to write a value under the associated [[URef]] 0b010 */
  case ACCESS_WRITE extends AccessRight(2)
  /* Permission to add to the value under the associated [[URef]]  0b100)*/
  case ACCESS_ADD extends AccessRight(4)
  /* Permission to read or write the value under the associated [[URef]] 0b11*/
  case ACCESS_READ_WRITE extends AccessRight(3)
  /* Permission to read or add to the value under the associated [[URef]] 0b101 */
  case ACCESS_READ_ADD extends AccessRight(5)
  /* Permission to add to, or write the value under the associated [[URef]] 0 b110*/
  case ACCESS_ADD_WRITE extends AccessRight(6)
  /* Permission to read, add to, or write the value under the associated [[URef]]  0b111*/
  case ACCESS_READ_ADD_WRITE extends AccessRight(7)

  def get(bits: Byte): AccessRight = {
    AccessRight.values.find(_.bits == bits).get
  }

}

object AccessRight {

  object AccessRight {

    import io.circe.{Decoder, Encoder}
    import io.circe.generic.semiauto.deriveEncoder
    import io.circe.generic.semiauto.deriveDecoder

    implicit val decoder: Decoder[AccessRight] = deriveDecoder[AccessRight]
    implicit val encoder: Encoder[AccessRight] = deriveEncoder[AccessRight]
  }

}