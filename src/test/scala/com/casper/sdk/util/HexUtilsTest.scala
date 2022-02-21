package com.casper.sdk.util

import org.scalatest.funsuite.AnyFunSuite
class HexUtilsTest  extends AnyFunSuite {

  test("HexUtils  toHex Test ") {
    HexUtils.toHex(Array.fill(1)(1.toByte))
    assert("01"==HexUtils.toHex(Array.fill(1)(1.toByte)).get)
  }

  test("HexUtils  fromHex Test ") {
   val bytes= HexUtils.fromHex("01")
   assert( java.util.Arrays.equals(Array.fill(1)(1.toByte),bytes.get))
  }

  test("HexUtils  fromHex Test with illegal hex litteral give None") {
 val bytes = HexUtils.fromHex("cx0114")
    assert(!bytes.isDefined)
  }

}