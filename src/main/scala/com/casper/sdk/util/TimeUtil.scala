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
  def timeStampString(epochMilliTime: Long): String = {
    val instant = Instant.ofEpochMilli(epochMilliTime)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"))
    formatter.format(instant)
  }

  /**
   * convert TTL into milliseconds
   *
   * @param ttl TTL string
   * @return milliseconds
   */
  def ttlToMillis(ttl: String): Long = {
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
        else throw new IllegalArgumentException(ttl + " is not a supported TTL format")
      }
    }
    catch {
      case e: NumberFormatException => throw new IllegalArgumentException(ttl + " is not a supported TTL format")
    }
    value
  }

  /**
   * Milliseconds to String TTL
   *
   * @param millis
   * @return human readable String TTL
   */
  def MillisToTtl(millis: Long): String = {
    if (millis < 0) throw new IllegalArgumentException("Millis must be greater than zero!")
    val sb = new StringBuilder("")
    var days = TimeUnit.MILLISECONDS.toDays(millis)
    var hours = TimeUnit.MILLISECONDS.toHours(millis) % 24
    var minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
    var seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
    val milliseconds = millis % 1000

    if (days > 0 && hours > 0) sb.append(days + "d ") else if (days > 0 && hours == 0) sb.append(days + "d")
    if (hours > 0 && minutes > 0) sb.append(hours + "h ") else if (hours > 0 && minutes == 0) sb.append(hours + "h")
    if (minutes > 0 && seconds > 0) sb.append(minutes + "m ") else if (minutes > 0 && seconds == 0) sb.append(minutes + "m")
    if (seconds > 0 && milliseconds > 0) sb.append(seconds + "s ") else if (seconds > 0 && milliseconds == 0) sb.append(seconds + "s")
    if (milliseconds > 0) sb.append(milliseconds + "ms")
    sb.toString()
  }
}
