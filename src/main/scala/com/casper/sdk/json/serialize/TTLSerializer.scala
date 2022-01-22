package com.casper.sdk.json.serialize

import com.casper.sdk.util.TimeUtil
import com.fasterxml.jackson.databind.util.StdConverter

/**
 * Custom serializer for TTL
 */
class TTLSerializer extends StdConverter[Long, String] {

  override def convert(value: Long): String = {
    TimeUtil.MillisToTtl(value)
  }
}
