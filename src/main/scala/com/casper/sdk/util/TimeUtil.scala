package com.casper.sdk.util

import java.time._

object TimeUtil {

  /**
   * Converts iso datetime to ms from unix epoch
   * @param timestamp
   * @return
   */
  def ToEpochMs(timestamp: String): Long={
    val dateTime = OffsetDateTime.parse(timestamp).toZonedDateTime
    dateTime.toInstant.toEpochMilli
  }
}
