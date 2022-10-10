package com.casper.sdk.util

import com.casper.sdk.rpc.RPCResult
import io.circe.generic.auto._
import io.circe.generic.semiauto.deriveDecoder
import io.circe.{Decoder, Encoder}
import io.circe.{Decoder, Encoder}
//import io.circe.generic.extras.auto._
//import io.circe.generic.extras.Configuration
//import io.circe.generic.semiauto
import io.circe.generic.semiauto.deriveEncoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.parser.decode
import io.circe.syntax._
import scala.reflect.ClassTag
import scala.util.{Failure, Success, Try}

object CirceConverter {



  def toJson[T:ClassTag](t: T)(implicit encoder: Encoder[T]): Try[String] = Try(t.asJson.noSpaces)

  def convertToObj[T](jsonStr: String)(implicit decoder: Decoder[T]): Try[T] =
    decode[T](jsonStr) match {
      case Right(value) => Success(value)
      case Left(error)  => Failure(error)
    }
}
