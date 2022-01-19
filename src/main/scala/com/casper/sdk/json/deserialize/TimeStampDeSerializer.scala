package com.casper.sdk.json.deserialize

import com.casper.sdk.types.cltypes.URef
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}
import com.casper.sdk.util.TimeUtil

import java.io.IOException

/**
 * Custom Json Deserializer for TimeStamp field
 */

class TimeStampDeSerializer extends JsonDeserializer[Long] {
  @throws[IOException]
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): Long = {
    TimeUtil.ToEpochMs(parser.getText)
  }
}