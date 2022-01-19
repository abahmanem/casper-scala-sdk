package com.casper.sdk.json.deserialize

import com.casper.sdk.util.TimeUtil
import com.fasterxml.jackson.databind.util.StdConverter

/**
 * Custom Json Deserializer for TimeStamp field
 */
class TimeStampDeSerializer extends  StdConverter[String,Long] {
  override def convert(value: String): Long = {
    TimeUtil.ToEpochMs(value)
  }
}
