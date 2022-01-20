package com.casper.sdk.serialization.domain.deploy

import com.casper.sdk.domain.deploy.DeployNamedArg
import com.casper.sdk.serialization.cltypes.CLValueByteSerializer
import com.casper.sdk.serialization.domain.deploy.DeployNamedArgByteSerializer
import com.casper.sdk.types.cltypes.{CLValue,CLPublicKey,URef}
import org.scalatest.funsuite.AnyFunSuite
import scala.collection.mutable.ArrayBuilder

/**
 * DeployNamedArgByteSerializerTest
 */

class DeployNamedArgByteSerializerTest extends AnyFunSuite {

  val serializer = new DeployNamedArgByteSerializer()

  def initBytebuilder : ArrayBuilder.ofByte = {
    val builder = new ArrayBuilder.ofByte
    builder.addAll(CLValue.U32("casper-test".getBytes().length).bytes) // name length
      .addAll("casper-test".getBytes()) //name byte array
  }

  test("Test serialize DeployNamedArg  with String CLValue") {
    val builder = initBytebuilder
    builder.addAll(new CLValueByteSerializer().toBytes(CLValue.String("Hello, World!")))
    var arg = new DeployNamedArg("casper-test",CLValue.String("Hello, World!"))
     assert(serializer.toBytes(arg).sameElements(builder.result()))
  }


  test("Test serialize DeployNamedArg  with U512 CLValue") {
    val builder = initBytebuilder
    builder.addAll(new CLValueByteSerializer().toBytes(CLValue.U512(999999999)))
    var arg = new DeployNamedArg("casper-test",CLValue.U512(999999999))
    assert(serializer.toBytes(arg).sameElements(builder.result()))
  }

  test("Test serialize DeployNamedArg  with Bytearray CLValue") {
    val builder = initBytebuilder
    val bytes = Array[Byte](1, -39, -65, 33, 72, 116, -118, -123, -56, -99, -91, -86, -40, -18, 11, 15, -62, -47, 5, -3, 57, -44, 26, 76, 121, 101, 54, 53, 79, 10, -30, -112, 12)
    builder .addAll(new CLValueByteSerializer().toBytes(CLValue.ByteArray(bytes)))
    var arg = new DeployNamedArg("casper-test",CLValue.ByteArray(bytes))
    assert(serializer.toBytes(arg).sameElements(builder.result()))
   }

  test("Test serialize DeployNamedArg  with CLPublicKey CLValue") {
    val builder = initBytebuilder
    builder .addAll(new CLValueByteSerializer().toBytes(CLValue.PublicKey("01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900c")))
    var arg = new DeployNamedArg("casper-test",CLValue.PublicKey("01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900c"))
    assert(serializer.toBytes(arg).sameElements(builder.result()))
  }

  test("Test serialize DeployNamedArg  with URef CLValue") {
    val builder = initBytebuilder
    builder .addAll(new CLValueByteSerializer().toBytes(CLValue.URef("uref-ebda3f171068107470bce0d74eb9a302fcb8914471fe8900c66fae258a0f46ef-007")))
    var arg = new DeployNamedArg("casper-test",CLValue.URef("uref-ebda3f171068107470bce0d74eb9a302fcb8914471fe8900c66fae258a0f46ef-007"))
    assert(serializer.toBytes(arg).sameElements(builder.result()))
  }

}
