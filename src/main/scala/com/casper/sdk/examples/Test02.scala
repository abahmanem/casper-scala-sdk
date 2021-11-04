package com.casper.sdk.examples

import com.casper.sdk._
import com.casper.sdk.core._
import com.casper.sdk.result._

import _root_.com.fasterxml.jackson.annotation.JsonInclude.Include
import _root_.com.fasterxml.jackson.core.`type`.TypeReference
import _root_.com.fasterxml.jackson.databind.json.JsonMapper
import _root_.com.fasterxml.jackson.databind.{DeserializationFeature, JsonNode, Module, ObjectMapper,SerializationFeature}
import _root_.com.fasterxml.jackson.module.scala.DefaultScalaModule
import _root_.com.fasterxml.jackson.core.util.DefaultIndenter
import _root_.com.fasterxml.jackson.core.util.DefaultPrettyPrinter





object Test02 extends App {
  val converter = new JsonConverterByClass

  val str : String = """ {"jsonrpc": "1.2","result": {"api_version" : "123", "peers" : [{"ip" : "one"}]}} """
  val d : GetPeersResult = converter.fromJson(str)

  println(converter.fromJson[GetPeersResult](str))
}
