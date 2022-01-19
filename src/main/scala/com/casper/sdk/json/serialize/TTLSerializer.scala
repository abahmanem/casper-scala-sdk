package com.casper.sdk.json.serialize

import com.casper.sdk.util.TimeUtil
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.{JsonSerializer, SerializerProvider}

class TTLSerializer extends JsonSerializer[Long] {
  override def serialize(value:Long, gen: JsonGenerator, serializers: SerializerProvider): Unit = {
    gen.writeString(TimeUtil.MillisToTtl(value))
  }
}