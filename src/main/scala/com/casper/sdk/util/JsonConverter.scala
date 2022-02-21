package com.casper.sdk.util

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.core.util.{DefaultIndenter, DefaultPrettyPrinter}
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.databind.{DeserializationFeature, SerializationFeature}
import com.fasterxml.jackson.module.scala.{ClassTagExtensions, DefaultScalaModule, JavaTypeable}

import scala.reflect.ClassTag
import scala.util.{Try, Success, Failure}

/**
 * JsonConverter Utility Object
 */

object JsonConverter {

  val mapper = JsonMapper.builder().addModule(DefaultScalaModule).build() :: ClassTagExtensions
  mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  val prettyPrinter = new DefaultPrettyPrinter
  prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE)
  mapper.writer(prettyPrinter)
  mapper.setSerializationInclusion(Include.NON_NULL)

  /**
   * convert a capser type to json
   *
   * @param a
   * @tparam A
   * @return
   */
  def toJson_[T](t: T): String = mapper.writer(prettyPrinter).writeValueAsString(t)

  /**
   *
   * @param json
   * @param m
   * @tparam V
   * @return
   */
  //def toMap[V](json: String)(implicit m: JavaTypeable[V]): Map[String, V] = fromJson[Map[String, V]](json)


  /**
   *
   * @param json
   * @param m
   * @tparam V
   * @return
   */
  def toList[V: ClassTag](json: String): List[V] =
    mapper.readValue(json)

  /**
   * Convert a json string into a Casper type
   *
   * @param json
   * @tparam A
   * @return
   */
  def fromJson_[T: ClassTag](json: String): T = {
    mapper.readValue(json, implicitly[ClassTag[T]].runtimeClass.asInstanceOf[Class[T]])
  }


  /**
   * convert a capser type to json
   *
   * @param a
   * @tparam A
   * @return
   */
  def toJson[T](t: T): Option[String] = Try {
    mapper.writer(prettyPrinter).writeValueAsString(t)
  } match {
    case Success(x) => Some(x)
    case Failure(err) => {
      print("Json serialization failed due to $err")
      None

    }
  }


  /**
   * Convert a json string into a Casper type
   *
   * @param json
   * @tparam A
   * @return
   */
  def fromJson[T: ClassTag](json: String): Option[T] = Try {
    mapper.readValue(json, implicitly[ClassTag[T]].runtimeClass.asInstanceOf[Class[T]])
  } match {
    case Success(x) => Some(x)
    case Failure(err) => {
      print("Json Deserialization failed due to $err")
      None
    }
  }
}
