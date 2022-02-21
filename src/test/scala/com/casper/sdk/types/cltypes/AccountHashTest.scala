package com.casper.sdk.types.cltypes

import com.casper.sdk.util.HexUtils
import org.scalatest.funsuite.AnyFunSuite

class AccountHashTest extends AnyFunSuite {
  val hex = "account-hash-85148dcd6c54b77e462a9acf387fb05aca953a83011db2c601716de0af1cf47c"
  val account = AccountHash(hex).get


  test("Test AccountHash  decode ") {
    assert(HexUtils.toHex(account.bytes).get.toLowerCase == "85148dcd6c54b77e462a9acf387fb05aca953a83011db2c601716de0af1cf47c".toLowerCase)
  }

  test("Test AccountHash formatAsHexAccount   ") {
    assert(account.format.toLowerCase == hex.toLowerCase)
  }

  test("Test new AccountHash with a non valid hex string  , throws IllegalArgumentException") {
    val account = AccountHash("acc-hash-85148dcd6c54b77e462a9acf387fb05aca953a83011db2c601716de0af1cf47c")
    assert(!account.isDefined)
  }
}
