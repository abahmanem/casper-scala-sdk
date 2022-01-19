package com.casper.sdk.json.serialize

import com.casper.sdk.types.cltypes.{CLByteArrayTypeInfo, CLTypeInfo}
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.{JsonSerializer, SerializerProvider}
import com.casper.sdk.types.cltypes.CLOptionTypeInfo

/**
 * 
 *  CLTypeInfo Custom Json serializer
 */

class CLTypeInfoSerializer extends JsonSerializer[CLTypeInfo] {
  override def serialize(value: CLTypeInfo, gen: JsonGenerator, serializers: SerializerProvider): Unit = {
    assert(value != null)

    if (value.isInstanceOf[CLByteArrayTypeInfo]) {
      gen.writeStartObject
      gen.writeFieldName(value.cl_Type.toString)
      gen.writeNumber(value.asInstanceOf[CLByteArrayTypeInfo].size)
      gen.writeEndObject
    }
    else if (value.isInstanceOf[CLOptionTypeInfo]) {
      gen.writeStartObject
      gen.writeFieldName(value.cl_Type.toString)
      gen.writeString(value.asInstanceOf[CLOptionTypeInfo].inner.cl_Type.toString)
      gen.writeEndObject
    }
    else gen.writeString(value.cl_Type.toString)
  }
}