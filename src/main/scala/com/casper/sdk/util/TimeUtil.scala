package com.casper.sdk.util

import java.time.{Instant, OffsetDateTime, ZoneId}
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

object TimeUtil {

  /**
   * Converts iso datetime to ms from unix epoch
   *
   * @param timestamp
   * @return
   */
  def ToEpochMs(timestamp: String): Long = {
    val dateTime = OffsetDateTime.parse(timestamp).toZonedDateTime
    dateTime.toInstant.toEpochMilli
  }

  /**
   * converts milliseconds to timestamp  eg : ": "2020-11-17T00:39:24.072Z"
   *
   * @param epochMilliTime
   * @return
   */
  def timeStampString(epochMilliTime: Long): String = {

    val instant = Instant.ofEpochMilli(epochMilliTime)
    val outFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"))
    outFormatter.format(instant)
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
    import scala.language.postfixOps
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
      else throw new IllegalArgumentException("not supported TTL format")
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
    if (days > 0) sb.append(days + "d ")
    var hours = TimeUnit.MILLISECONDS.toHours(millis) % 24
    if (hours > 0) sb.append(hours + "h ")
    var minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
    if (minutes > 0) sb.append(minutes + "m ")
    var seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
    if (seconds > 0) sb.append(seconds + "s ")
    val milliseconds = millis % 1000
    if (milliseconds > 0) sb.append(milliseconds + "ms")
    sb.toString()
  }

}
