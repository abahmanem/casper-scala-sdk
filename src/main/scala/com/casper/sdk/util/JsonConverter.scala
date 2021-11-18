package com.casper.sdk.util

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.core.util.{DefaultIndenter, DefaultPrettyPrinter}
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.databind.{DeserializationFeature, SerializationFeature}
import com.fasterxml.jackson.module.scala.{ClassTagExtensions, DefaultScalaModule, JavaTypeable}

import java.io.{IOException, InputStream, OutputStream}
import scala.reflect.ClassTag

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
  def toJson[T](t: T): String = {
    mapper.writer(prettyPrinter).writeValueAsString(t)
  }

  /**
   *
   * @param json
   * @param m
   * @tparam V
   * @return
   */
  def toMap[V](json: String)(implicit m: JavaTypeable[V]): Map[String, V] = fromJson[Map[String, V]](json)

  /**
   * Convert a json string into a Casper type
   *
   * @param json
   * @tparam A
   * @return
   */
  def fromJson[T: ClassTag](json: String): T = {
    mapper.readValue(json, implicitly[ClassTag[T]].runtimeClass.asInstanceOf[Class[T]])
  }

  /**
   * Writes a Casper type object to an  OutputStream
   *
   * @param clObject
   * @param out
   * @throws
   */
  @throws[IOException]
  def toJson[T: ClassTag](t: T, out: OutputStream): Unit = {
    mapper.writer(prettyPrinter).writeValue(out, t)
    out.close()
  }

  /**
   * Parses JSON from an inputstream into a casper type object
   *
   * @param in
   * @param t
   * @tparam T
   * @throws IOException
   * @return T
   */

  @throws[IOException]
  def fromJson[T: ClassTag](in: InputStream, t: T): T = {
    mapper.reader.readValue[T](in)
  }
}
