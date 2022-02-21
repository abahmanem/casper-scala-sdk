package com.casper.sdk.json.deserialize
import com.casper.sdk.types.cltypes.Signature
import com.casper.sdk.util.JsonConverter
import org.scalatest.funsuite.AnyFunSuite

/**
 * SignatureDeserializerTest
 */

class SignatureDeserializerTest extends AnyFunSuite {
  test("Deserialize CLPublicKey") {
    val jsonsig = """ "012dbf03817a51794a8e19e0724884075e6d1fbec326b766ecfa6658b41f81290da85e23b24e88b1c8d9761185c961daee1adab0649912a6477bcd2e69bd91bd08" """
    val signature = JsonConverter.fromJson[Signature](jsonsig).get
    info("Signature is not null")
    assert(signature != null)
    info("signature.bytes  is the same as new Signature(\"012dbf03817a51794a8e19e0724884075e6d1fbec326b766ecfa6658b41f81290da85e23b24e88b1c8d9761185c961daee1adab0649912a6477bcd2e69bd91bd08\").bytes")
    assert(signature.bytes.sameElements(Signature("012dbf03817a51794a8e19e0724884075e6d1fbec326b766ecfa6658b41f81290da85e23b24e88b1c8d9761185c961daee1adab0649912a6477bcd2e69bd91bd08").get.bytes))
  }
}