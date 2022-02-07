package com.casper.sdk.util
import org.scalatest.funsuite.AnyFunSuite
class ByteUtilTest extends AnyFunSuite {

  test("ByteUtils join Test ") {
    val array1 = Array.fill(1)(0.toByte)
    val array2 = Array.fill(2)(1.toByte)
    val array3 = Array.fill(3)(0x08.toByte)
    val array = ByteUtils.join(array1,array2,array3)
    assert(java.util.Arrays.equals(array,Array[Byte](0,1,1,8,8,8)))
  }
}