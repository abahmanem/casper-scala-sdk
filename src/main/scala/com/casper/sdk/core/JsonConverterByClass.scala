package com.casper.sdk.core

import _root_.com.fasterxml.jackson.annotation.JsonInclude.Include
import _root_.com.fasterxml.jackson.core.`type`.TypeReference
import _root_.com.fasterxml.jackson.databind.json.JsonMapper
import _root_.com.fasterxml.jackson.databind.{DeserializationFeature, JsonNode, Module, ObjectMapper,SerializationFeature}
import _root_.com.fasterxml.jackson.module.scala.DefaultScalaModule
import _root_.com.fasterxml.jackson.core.util.DefaultIndenter
import _root_.com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import scala.reflect.runtime.universe._
import scala.reflect.ClassTag
import scala.reflect._


class JsonConverterByClass{
  val mapper: ObjectMapper  = JsonConverterByClass.createMapper()

  /**
   * convert a capser type to json
   * @param a
   * @tparam A
   * @return
   */
  def toJson[T](t: T): String = {

    val prettyPrinter = new DefaultPrettyPrinter
    prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE)
    mapper.writer(prettyPrinter).writeValueAsString(t)
  }

  /**
   * Convert a json string into a Casper type
   * @param json
   * @tparam A
   * @return
   */
  def fromJson[T : ClassTag](json: String) : T  = mapper.readValue(json, implicitly[ClassTag[T]].runtimeClass.asInstanceOf[Class[T]])
  /**
   * Convert a json string into a Casper type
   * @param json
   * @tparam A
   * @return
   */
  // def fromJson[T](json: String): T = mapper.readValue(json, new TypeReference[T] {})
}

object JsonConverterByClass {
  def createMapper(): ObjectMapper  = {
    val mapper  = JsonMapper.builder().addModule(DefaultScalaModule).build()
    mapper.registerModule(DefaultScalaModule)
   // SerializationFeature.FAIL_ON_EMPTY_BEANS
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false)
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    mapper.setSerializationInclusion(Include.NON_NULL)
    mapper
  }
}
