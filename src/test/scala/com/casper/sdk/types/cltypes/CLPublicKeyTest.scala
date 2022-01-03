package com.casper.sdk.types.cltypes

import com.casper.sdk.util.HexUtils
import org.scalatest.funsuite.AnyFunSuite

class CLPublicKeyTest extends AnyFunSuite {
  val hex = "01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900c"
  val key = new CLPublicKey(hex)

  test("Test CLPublicKey KeyAlgorithm = ED25519") {
    assert(key.keyAlgorithm== KeyAlgorithm.ED25519)
  }

  test("Test CLPublicKey  decode ") {
    assert(HexUtils.toHex(key.bytes).toLowerCase== "d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900c".toLowerCase)
  }

  test("Test CLPublicKey formatAsHexAccount   ") {
    assert(key.formatAsHexAccount.toLowerCase == hex.toLowerCase)
  }

  test("Test new CLPublicKey with a non valid hex string  , throws IllegalArgumentException") {
    val caught:  IllegalArgumentException = intercept[IllegalArgumentException] {
      new CLPublicKey("9cfggg77ft07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d06555f0363")
    }
    assert(caught.getMessage == "Unable to decode: 9cfggg77ft07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d06555f0363")
  }

}
