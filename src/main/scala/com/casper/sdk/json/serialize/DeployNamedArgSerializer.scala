package com.casper.sdk.json.serialize

import com.casper.sdk.domain.deploy.DeployNamedArg
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.{JsonSerializer, SerializerProvider}

/**
 * DeployNamedArg Custom Json serializer
 */

class DeployNamedArgSerializer extends JsonSerializer[DeployNamedArg] {
  override def serialize(value: DeployNamedArg, gen: JsonGenerator, serializers: SerializerProvider): Unit = {
    require(value != null)
    gen.writeStartArray
    gen.writeString(value.name)
    gen.getCodec.writeValue(gen, value.value)
    gen.writeEndArray()
  }
}
