package com.casper.sdk.util

import com.casper.sdk.types.cltypes.KeyAlgorithm
import com.casper.sdk.util.TimeUtil
import org.scalatest.funsuite.AnyFunSuite

class TimeUtilTest extends AnyFunSuite {

  test("Test ToEpochMs ") {
    info("TimeUtil.ToEpochMs(\"2020-11-17T00:39:24.072Z\") == 1605573564072L")
    assert(TimeUtil.ToEpochMs("2020-11-17T00:39:24.072Z").get == 1605573564072L)
  }

  test("Test ToEpochMs with a wrong timestamp, throws NoSuchElementException ") {
    val caught: NoSuchElementException = intercept[NoSuchElementException] {
      info("TimeUtil.ToEpochMs(\"202011-17T00:39:24.072Z\") throws NoSuchElementException")
      TimeUtil.ToEpochMs("202011-17T00:39:24.072Z").get
    }
  }

  test("Test timeStampString from millis") {
    info("TimeUtil.timeStampString(1605573564072L)==\"2020-11-17T00:39:24.072Z\"")
    assert(TimeUtil.timeStampString(1605573564072L).get == "2020-11-17T00:39:24.072Z")
  }

  test("Test ttlToMillis ") {

    info("TimeUtil.ttlToMillis (\"30m\") ==1800000L")
    assert(TimeUtil.ttlToMillis("30m").get == 1800000L)
    info("TimeUtil.ttlToMillis (\"30m\") ==1800000L")
    assert(TimeUtil.ttlToMillis("1h 30m").get == 5400000L)
  }

  test("Test ttlToMillis with a non supported TTL gives None String Option ") {
    info("TimeUtil.ttlToMillis (\"3041mn\") gives None String Option")
    val time = TimeUtil.ttlToMillis("3041mn")
    assert(!time.isDefined)
  }

  test("Test MillisToTtl") {
    info("TimeUtil.MillisToTtl (1800000L)==\"30m\"")
    assert(TimeUtil.MillisToTtl(1800000L).get == "30m")
    info("TimeUtil.MillisToTtl (5400000L)==\"1h 30m\"")
    assert(TimeUtil.MillisToTtl(5400000L).get == "1h 30m")
  }

  test("Test MillisToTtl with negative millis ,  gives None String Option") {
    info("TimeUtil.MillisToTtl (-5L) gives None String Option ")
    val time = TimeUtil.MillisToTtl(-5L)
    assert(!time.isDefined)
  }
}
