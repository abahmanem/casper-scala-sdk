package com.casper.sdk.serialization.domain.deploy

import com.casper.sdk.domain.deploy.DeployApproval
import com.casper.sdk.serialization.domain.deploy.DeployApprovalByteSerializer
import com.casper.sdk.types.cltypes.{CLPublicKey, Signature}
import com.casper.sdk.util.HexUtils
import org.scalatest.funsuite.AnyFunSuite

/**
 * DeployApprovalByteSerializerTest
 */
class DeployApprovalByteSerializerTest extends AnyFunSuite {


  test("Test DeployApprovalByteSerializer with a signle DeployApproval  ") {
    val serializer = new DeployApprovalByteSerializer()
    val signer=  CLPublicKey("01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900c")
    val signature= Signature("012dbf03817a51794a8e19e0724884075e6d1fbec326b766ecfa6658b41f81290da85e23b24e88b1c8d9761185c961daee1adab0649912a6477bcd2e69bd91bd08")
    val approval = new DeployApproval(signer,signature)
    assert("01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900c012dbf03817a51794a8e19e0724884075e6d1fbec326b766ecfa6658b41f81290da85e23b24e88b1c8d9761185c961daee1adab0649912a6477bcd2e69bd91bd08"== HexUtils.toHex(serializer.toBytes(approval)).get)
  }
}
