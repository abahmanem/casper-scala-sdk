package com.casper.sdk.json.serialize

import com.casper.sdk.types.cltypes.CLValue
import com.casper.sdk.util.HexUtils
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.{JsonSerializer, SerializerProvider}

/**
 * CLValue Custom Json serializer
 */

class CLValueSerializer extends JsonSerializer[CLValue] {
  override def serialize(value: CLValue, gen: JsonGenerator, serializers: SerializerProvider): Unit = {
    assert(value != null)
    gen.writeStartObject
    gen.writeFieldName("cl_type")
    gen.getCodec.writeValue(gen, value.cl_infoType)
    gen.writeFieldName("bytes")
    gen.writeString(HexUtils.toHex(value.bytes))
    parsed(value, gen)
    gen.writeEndObject
  }

    /**
     * Write parsed tag
     * @param value
     * @param gen
     */
    def parsed(value: CLValue, gen: JsonGenerator) = {
      if (value.parsed != null) {
        gen.writeFieldName("parsed")
        if (value.parsed.isInstanceOf[Number]) gen.writeNumber(value.parsed.toString)
        else gen.writeString(value.parsed.toString)
      }
    }

}

