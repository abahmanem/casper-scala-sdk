package com.casper.sdk.serialization.domain.deploy

import com.casper.sdk.crypto.hash.Hash
import com.casper.sdk.domain.deploy.DeployHeader
import com.casper.sdk.serialization.domain.deploy.DeployHeaderByteSerializer
import com.casper.sdk.types.cltypes.{CLPublicKey, CLValue}
import com.casper.sdk.util.{HexUtils, TimeUtil}
import org.scalatest.funsuite.AnyFunSuite

/**
 * DeployHeaderByteSerializerTest
 */
class DeployHeaderByteSerializerTest extends AnyFunSuite {

  val serializer = new DeployHeaderByteSerializer()
  test("Test serialize DeployHeader ") {
    val header = new DeployHeader( CLPublicKey("01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900c"),
      "2022-10-05T16:17:42.822Z", "30m", 1,
      Hash("4811966d37fe5674a8af4001884ea0d9042d1c06668da0c963769c3a01ebd08f"),
      Seq( Hash("0101010101010101010101010101010101010101010101010101010101010101").get),
      "casper-example")
    print(HexUtils.toHex(serializer.toBytes(header).get).get)
    assert("01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900ca647f0a88301000040771b000000000001000000000000004811966d37fe5674a8af4001884ea0d9042d1c06668da0c963769c3a01ebd08f0100000001010101010101010101010101010101010101010101010101010101010101010e0000006361737065722d6578616d706c65"
      == HexUtils.toHex(serializer.toBytes(header).get).get)
  }
}
