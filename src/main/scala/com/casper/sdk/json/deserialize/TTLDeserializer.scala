package com.casper.sdk.json.deserialize

import com.casper.sdk.util.TimeUtil
import com.fasterxml.jackson.databind.util.StdConverter

/**
 * Custom Json Converter for TTl field
 */

class TTLDeserializer extends  StdConverter[String,Long] {
  override def convert(value: String): Long = {
    TimeUtil.ttlToMillis(value)
  }
}
