package com.casper.sdk.domain.deploy

import com.casper.sdk.types.cltypes.{CLPublicKey, CLValue}

/**
 * DeployNamedArg entiyt class
 */
case class DeployNamedArg(
                           name: String,
                           value: Option[CLValue]
                         ) {

  /**
   * constructors with CLValues
   *
   * @param name
   * @param long
   */
  def this(name: String, byte: Byte) = this(name, CLValue.U8(byte))

  def this(name: String, long: Long) = this(name, CLValue.I64(long))

  def this(name: String, bool: Boolean) = this(name, CLValue.Bool(bool))

  def this(name: String, int: Int) = this(name, CLValue.I32(int))

  def this(name: String, byteArr: Array[Byte]) = this(name, CLValue.ByteArray(byteArr))

  def this(name: String, bigint: BigInt) = this(name, CLValue.U512(bigint))

  def this(name: String, str: String) = this(name, CLValue.String(str))
}


object DeployNamedArg {

  import io.circe.{Decoder, Encoder, HCursor, Json}
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

  implicit val encoder: Encoder[DeployNamedArg] = new Encoder[DeployNamedArg] {
    final def apply(value: DeployNamedArg): Json = Json.arr(Json.fromString(value.name), value.value.asJson)
  }

  implicit val decoder: Decoder[DeployNamedArg] = new Decoder[DeployNamedArg] {
    final def apply(c: HCursor): Decoder.Result[DeployNamedArg] =
      for {
        name <- c.value.asArray.get(0).as[String] //  c.value.asArray.get(0).as[String]
        clvalue <- c.value.asArray.get(1).as[CLValue]
      } yield {
        new DeployNamedArg(name, Option(clvalue))
      }
  }
}