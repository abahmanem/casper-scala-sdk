package com.casper.sdk.json.serialize

import com.casper.sdk.util.TimeUtil
import com.fasterxml.jackson.databind.util.StdConverter

/**
 * Custom serializer for TTL
 */
class TTLSerializer extends StdConverter[Option[Long], String] {

  override def convert(value: Option[Long]): String = {
    if (value.isDefined)
    TimeUtil.MillisToTtl(value.get).get
    else ""
  }
}
