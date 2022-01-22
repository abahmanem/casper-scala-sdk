package com.casper.sdk.util

import com.casper.sdk.types.cltypes.KeyAlgorithm
import com.casper.sdk.util.TimeUtil
import org.scalatest.funsuite.AnyFunSuite

class TimeUtilTest extends AnyFunSuite {

  test("Test ToEpochMs ") {
    info("TimeUtil.ToEpochMs(\"2020-11-17T00:39:24.072Z\") == 1605573564072L")
    assert(TimeUtil.ToEpochMs("2020-11-17T00:39:24.072Z").get == 1605573564072L)
  }

  test("Test ToEpochMs with a wrong timestamp, throws IllegalArgumentException ") {
    val caught: IllegalArgumentException = intercept[IllegalArgumentException] {
      info("TimeUtil.ToEpochMs(\"202011-17T00:39:24.072Z\") throws IllegalArgumentException")
      TimeUtil.ToEpochMs("202011-17T00:39:24.072Z")
    }
    assert(caught.getMessage == "Text 202011-17T00:39:24.072Z could not be parsed as a date")
  }

  test("Test timeStampString from millis") {
    info("TimeUtil.timeStampString(1605573564072L)==\"2020-11-17T00:39:24.072Z\"")
    assert(TimeUtil.timeStampString(1605573564072L) == "2020-11-17T00:39:24.072Z")
  }

  test("Test ttlToMillis ") {

    info("TimeUtil.ttlToMillis (\"30m\") ==1800000L")
    assert(TimeUtil.ttlToMillis("30m") == 1800000L)
    info("TimeUtil.ttlToMillis (\"30m\") ==1800000L")
    assert(TimeUtil.ttlToMillis("1h 30m") == 5400000L)
  }

  test("Test ttlToMillis with a non supported TTL , Thorws IllegalArgumentException ") {

    val caught: IllegalArgumentException = intercept[IllegalArgumentException] {
      info("TimeUtil.ttlToMillis (\"3041mn\") throws IllegalArgumentException")
      TimeUtil.ttlToMillis("3041mn")
    }
    assert(caught.getMessage == "3041mn is not a supported TTL format")
  }

  test("Test MillisToTtl") {
    info("TimeUtil.MillisToTtl (1800000L)==\"30m\"")
    assert(TimeUtil.MillisToTtl(1800000L) == "30m")

    info("TimeUtil.MillisToTtl (5400000L)==\"1h 30m\"")
    assert(TimeUtil.MillisToTtl(5400000L) == "1h 30m")
  }

  test("Test MillisToTtl with negative millis ,  Thorws IllegalArgumentException") {
    val caught: IllegalArgumentException = intercept[IllegalArgumentException] {
      info("TimeUtil.MillisToTtl (-5L) throws IllegalArgumentException")
      TimeUtil.MillisToTtl(-5L)
    }
    assert(caught.getMessage == "Millis must be greater than zero!")
  }
}
