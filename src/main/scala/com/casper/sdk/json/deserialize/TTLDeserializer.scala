package com.casper.sdk.json.deserialize

import com.casper.sdk.util.TimeUtil
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}

import java.io.IOException

/**
 * Custom Json Deserializer for TTl field
 */

class TTLDeserializer extends JsonDeserializer[Long] {
  @throws[IOException]
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): Long = {
    TimeUtil.ttlToMillis(parser.getText)
  }
}