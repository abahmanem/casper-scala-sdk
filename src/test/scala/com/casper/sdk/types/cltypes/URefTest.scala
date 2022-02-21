package com.casper.sdk.types.cltypes
import com.casper.sdk.util.HexUtils
import org.scalatest.funsuite.AnyFunSuite

class URefTest extends AnyFunSuite {
  val ref = "uref-bC3c71eA5246EFA149CA653E6F63192e7c59C652Ab830eC59b234B99bfA0B109-007"
  val uref = URef(ref)

  test("Test Uref AccessRight") {
    assert(uref.get.accessRights == AccessRight.ACCESS_READ_ADD_WRITE)
  }

  test("Test Uref decode ") {
    assert(HexUtils.toHex(uref.get.bytes).get.toLowerCase == "bC3c71eA5246EFA149CA653E6F63192e7c59C652Ab830eC59b234B99bfA0B109".toLowerCase)
  }

  test("Test format Uref  ") {
    assert(uref.get.format.toLowerCase == ref.toLowerCase)
  }

  test("Test new Uref with a non valid hex string ") {
    val uref = URef("uref-9cC6877ft07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d067C4D0363-007")
    assert(!uref.isDefined)
 }
  test("Test new Uref with a non uref string ") {
    val uref = URef("9cC6877ft07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d067C4D0363-007")
    assert(!uref.isDefined)
  }

}
