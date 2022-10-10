package com.casper.sdk.util

import io.circe.generic.auto._
import io.circe.{Decoder, Encoder}
import io.circe.{Decoder, Encoder}
import io.circe.parser.decode
import io.circe.syntax._
import scala.reflect.ClassTag
import scala.util.{Failure, Success, Try}

/**
 * Json serializing/deserilizing utils
 */
object CirceConverter {


  /**
   * encode casper types
   * @param t
   * @param encoder
   * @tparam T
   * @return Try[t.asJson.noSpaces]
   */
  def toJson[T:ClassTag](t: T)(implicit encoder: Encoder[T]): Try[String] = Try(t.asJson.noSpaces)

  /**
   * decode casper types
   * @param jsonStr
   * @param decoder
   * @tparam T
   * @return Try[T]
   */

  def convertToObj[T](jsonStr: String)(implicit decoder: Decoder[T]): Try[T] =
    decode[T](jsonStr) match {
      case Right(value) => Success(value)
      case Left(error)  => Failure(error)
    }
}
