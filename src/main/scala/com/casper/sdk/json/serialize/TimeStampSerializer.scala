package com.casper.sdk.json.serialize

import com.casper.sdk.types.cltypes.CLPublicKey
import com.casper.sdk.util.TimeUtil
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.{JsonSerializer, SerializerProvider}

/**
 * Custom Json Serializer for TimeStamp : 1620138035104---->"2021-05-04T14:20:35.104Z"
 */
class TimeStampSerializer extends JsonSerializer[Long] {
  override def serialize(value:Long, gen: JsonGenerator, serializers: SerializerProvider): Unit = {
    gen.writeString(TimeUtil.timeStampString(value))
  }
}