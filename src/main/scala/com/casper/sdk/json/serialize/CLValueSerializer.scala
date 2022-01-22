package com.casper.sdk.json.serialize

import com.casper.sdk.types.cltypes._
import com.casper.sdk.util.HexUtils
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.node.{ArrayNode, ObjectNode}
import com.fasterxml.jackson.databind.{JsonSerializer, SerializerProvider}

/**
 * CLValue Custom Json serializer
 */

class CLValueSerializer extends JsonSerializer[CLValue] {
  override def serialize(value: CLValue, gen: JsonGenerator, serializers: SerializerProvider): Unit = {
    require(value != null)
    gen.writeStartObject
    gen.writeFieldName("cl_type")
    gen.getCodec.writeValue(gen, value.cl_infoType)
    gen.writeFieldName("bytes")
    gen.writeString(HexUtils.toHex(value.bytes).get)
    parsed(value, gen)
    gen.writeEndObject
  }

  /**
   * Write parsed tag
   *
   * @param value
   * @param gen
   */
  def parsed(value: CLValue, gen: JsonGenerator) = {
    if (value.parsed != null) {
      gen.writeFieldName("parsed")
      if (value.parsed.isInstanceOf[Number]) gen.writeString(value.parsed.toString)
      else if (value.parsed.isInstanceOf[Array[Any]])
        parsedForArray(value.parsed.asInstanceOf[Array[Any]], gen)
      else  gen.writeString(value.parsed.toString)
    }
  }

  /**
   * compute parsed value for an array (List, Tuples)
   *
   * @param array
   * @param gen
   */
  def parsedForArray(array: Array[Any], gen: JsonGenerator): Unit = {
    if (array.length > 0) {
      val parsed = new StringBuilder("[")
      for (j <- 0 to array.length - 1) {
        if (j < array.length - 1)
          parsed.append("\"").append(array(j)).append("\",")
        else
          parsed.append("\"").append(array(array.length - 1)).append("\"")
      }
      parsed.append("]")
      gen.writeString(parsed.toString())
    }
    else
      gen.writeString("")
  }
}



