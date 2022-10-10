package com.casper.sdk.json.deserialize

import com.casper.sdk.types.cltypes.CLPublicKey
import com.casper.sdk.util.CirceConverter
import org.scalatest.funsuite.AnyFunSuite

/**
 * CLPublicKeyDeserializerTest
 */
class CLPublicKeyDeserializerTest extends AnyFunSuite {
  test("Deserialize CLPublicKey") {
    val key = """ "017d96b9A63ABCB61C870a4f55187A0a7AC24096Bdb5Fc585c12a686a4D892009e" """
    val pubKey = CirceConverter.convertToObj[Option[CLPublicKey]](key).get
    info("PublicKey is not null")
    assert(pubKey.isDefined)
    info("pubKey.bytes  is the same as new CLPublicKey(\"017d96b9A63ABCB61C870a4f55187A0a7AC24096Bdb5Fc585c12a686a4D892009e\").bytes")
    assert(pubKey.get.bytes.sameElements(CLPublicKey("017d96b9A63ABCB61C870a4f55187A0a7AC24096Bdb5Fc585c12a686a4D892009e").get.bytes))//.get.bytes))
  }
}
