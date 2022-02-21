package com.casper.sdk.json.deserialize

import com.casper.sdk.util.TimeUtil
import com.fasterxml.jackson.databind.util.StdConverter

/**
 * Custom Json Converter for TimeStamp field
 */
class TimeStampDeSerializer extends  StdConverter[String,Option[Long]] {
  override def convert(value: String): Option[Long] = {
    TimeUtil.ToEpochMs(value)
  }
}
