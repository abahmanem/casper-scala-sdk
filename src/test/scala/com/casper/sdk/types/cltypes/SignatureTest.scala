package com.casper.sdk.types.cltypes

import com.casper.sdk.util.HexUtils
import org.scalatest.funsuite.AnyFunSuite

/**
 * SignatureTest
 */
class SignatureTest extends AnyFunSuite {
  val hex = "012dbf03817a51794a8e19e0724884075e6d1fbec326b766ecfa6658b41f81290da85e23b24e88b1c8d9761185c961daee1adab0649912a6477bcd2e69bd91bd08"
  val signature = Signature(hex).get

  test("Test Signature KeyAlgorithm = ED25519") {
    assert(signature.keyAlgorithm == KeyAlgorithm.ED25519)
  }

  test("Test Signature  decode ") {
    assert(HexUtils.toHex(signature.bytes).get.toLowerCase == "2dbf03817a51794a8e19e0724884075e6d1fbec326b766ecfa6658b41f81290da85e23b24e88b1c8d9761185c961daee1adab0649912a6477bcd2e69bd91bd08".toLowerCase)
  }

  test("Test Signature formatAsHexAccount   ") {
    assert(signature.formatAsHexAccount.get .toLowerCase == hex.toLowerCase)
  }

  test("Test new Signature with a non valid hex string  gives None") {
    val sig =   Signature("erftt78817a51794a8e19e0724884075e6d1fbec326b766ecfa6658b41f81290da85e23b24e88b1c8d9761185c961daee1adab0649912a6477bcd2e69bd91bd08")
    assert(!sig.isDefined)
  }
}