package com.casper.sdk.serialization.domain.deploy

import com.casper.sdk.crypto.hash.Hash
import com.casper.sdk.domain.deploy.DeployHeader
import com.casper.sdk.serialization.domain.deploy.DeployHeaderByteSerializer
import com.casper.sdk.types.cltypes.{CLPublicKey, CLValue}
import com.casper.sdk.util.HexUtils
import org.scalatest.funsuite.AnyFunSuite

/**
 * DeployHeaderByteSerializerTest
 */
class DeployHeaderByteSerializerTest extends AnyFunSuite {

  val serializer = new DeployHeaderByteSerializer()

  test("Test serialize DeployHeader ") {
    val header = new DeployHeader( CLPublicKey("01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900c"),
      Option(1605573564072L), Option(3600000), 1,
      Some(new Hash("4811966d37fe5674a8af4001884ea0d9042d1c06668da0c963769c3a01ebd08f")),
      Seq(new Hash("0101010101010101010101010101010101010101010101010101010101010101")),
      "casper-example")
    assert("01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900ca856a4d37501000080ee36000000000001000000000000004811966d37fe5674a8af4001884ea0d9042d1c06668da0c963769c3a01ebd08f0100000001010101010101010101010101010101010101010101010101010101010101010e0000006361737065722d6578616d706c65" == HexUtils.toHex(serializer.toBytes(header)).get)
  }
}
