package com.casper.sdk.types.cltypes
import com.casper.sdk.util.HexUtils
import org.scalatest.funsuite.AnyFunSuite

class URefTest extends AnyFunSuite {
  val ref = "uref-bC3c71eA5246EFA149CA653E6F63192e7c59C652Ab830eC59b234B99bfA0B109-007"
  val uref = new URef(ref)

  test("Test Uref AccessRight") {
    assert(uref.accessRights== AccessRight.ACCESS_READ_ADD_WRITE)
  }

  test("Test Uref decode ") {
    assert(HexUtils.toHex(uref.bytes).toLowerCase== "bC3c71eA5246EFA149CA653E6F63192e7c59C652Ab830eC59b234B99bfA0B109".toLowerCase)
  }

  test("Test format Uref  ") {
    assert(uref.format.toLowerCase == ref.toLowerCase)
  }

  test("Test new Uref with a non valid hex string  , throws IllegalArgumentException") {
    val caught:  IllegalArgumentException = intercept[IllegalArgumentException] {
      new URef("uref-9cC6877ft07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d067C4D0363-007")
    }
    assert(caught.getMessage == "Unable to decode: 9cC6877ft07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d067C4D0363")
  }

}
