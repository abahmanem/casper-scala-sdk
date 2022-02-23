package com.casper.sdk.util

import com.casper.sdk.rpc.exceptions.RPCIOException

import java.time.format.{DateTimeFormatter, DateTimeParseException}
import java.time.{Instant, OffsetDateTime, ZoneId}
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

object TimeUtil {

  /**
   * Converts iso datetime to ms from unix epoch
   *
   * @param timestamp
   * @return
   */
  def ToEpochMs(timestamp: String): Option[Long] = try {
    val dateTime = OffsetDateTime.parse(timestamp).toZonedDateTime
    Some(dateTime.toInstant.toEpochMilli)
  }
  catch {
    case e: DateTimeParseException => None
  }
  /**
   * converts milliseconds to timestamp  eg : ": "2020-11-17T00:39:24.072Z"
   *
   * @param epochMilliTime
   * @return
   */
  def timeStampString(epochMilliTime: Long): Option[String] = {
    if (epochMilliTime < 0) None
    else {
      val instant = Instant.ofEpochMilli(epochMilliTime)
      val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"))
      Some(formatter.format(instant))
    }
  }

  /**
   * convert TTL into milliseconds
   *
   * @param ttl TTL string
   * @return milliseconds
   */
  def ttlToMillis(ttl: String): Option[Long] = {
    var value: Long = 0
    val p = ttl.split(' ')
    try {
      for (v <- p) {
        if (v.contains("ms"))
          value += (v.replace("ms", "")).toLong
        else if (v.contains("s"))
          value += (v.replace("s", "")).toLong * 1000
        else if (v.contains("m"))
          value += (v.replace("m", "")).toLong * 60000
        else if (v.contains("h"))
          value += (v.replace("h", "")).toLong * 3600000
        else if (v.contains("d"))
          value += (v.replace("d", "")).toLong * 3600000 * 24
        else if (v.contains("day"))
          value += (v.replace("day", "")).toLong * 3600000 * 24
      }
    }
    catch {
      case e: NumberFormatException => None
    }
    value match {
      case 0 => None
      case _ => Some(value)
    }
  }

  /**
   * Milliseconds to String TTL
   *
   * @param millis
   * @return human readable String TTL
   */
  def MillisToTtl(millis: Long): Option[String] = {
    if (millis < 0) None
    else {
      val sb = new StringBuilder("")
      var days = TimeUnit.MILLISECONDS.toDays(millis)
      var hours = TimeUnit.MILLISECONDS.toHours(millis) % 24
      var minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
      var seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
      val milliseconds = millis % 1000
      val day = "d"
      val hour = "h"
      val minute = "m"
      val second = "s"
      val mills = "ms"

      sb.append(if (days > 0 && hours > 0) s"$days$day " else if (days > 0 && hours == 0) s"$days$day" else "")
        .append(if (hours > 0 && minutes > 0) s"$hours$hour " else if (hours > 0 && minutes == 0) s"$hours$hour" else "")
        .append(if (minutes > 0 && seconds > 0) s"$minutes$minute " else if (minutes > 0 && seconds == 0) s"$minutes$minute" else "")
        .append(if (seconds > 0 && milliseconds > 0) s"$seconds$second " else if (seconds > 0 && milliseconds == 0) s"$seconds$second" else "")
        .append(if (seconds > 0 && milliseconds > 0) s"$seconds$second " else if (seconds > 0 && milliseconds == 0) s"$seconds$second" else "")
        .append(if (milliseconds > 0) s"$milliseconds$mills" else "")

      Some(sb.toString())
    }
  }
}
