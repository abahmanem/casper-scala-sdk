package com.casper.sdk.serialization.cltypes

import com.casper.sdk.serialization.cltypes.CLPublicKeySerializer
import com.casper.sdk.types.cltypes.CLPublicKey
import org.scalatest.funsuite.AnyFunSuite

class CLPublicKeySerializerTest extends AnyFunSuite {


  test("Test serialize CLPublicKey ") {
    val serializer = new CLPublicKeySerializer()
    val pbukey =  CLPublicKey("01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900c")
    val serialized = Array[Byte](1, -39, -65, 33, 72, 116, -118, -123, -56, -99, -91, -86, -40, -18, 11, 15, -62, -47, 5, -3, 57, -44, 26, 76, 121, 101, 54, 53, 79, 10, -30, -112, 12)
    assert(serializer.toBytes(pbukey.get).sameElements(serialized))
  }
}
