package com.casper.sdk.serialization.domain.deploy

import com.casper.sdk.crypto.hash.Hash
import com.casper.sdk.domain.deploy.{DeployNamedArg, DeployTransfer, ModuleBytes, StoredContractByHash, StoredContractByName, StoredVersionedContractByHash, StoredVersionedContractByName}
import com.casper.sdk.serialization.domain.deploy.DeployExecutableByteSerializer
import com.casper.sdk.types.cltypes.{CLPublicKey, CLValue}
import com.casper.sdk.util.HexUtils
import org.scalatest.funsuite.AnyFunSuite

/**
 * DeployExecutableByteSerializerTest
 */
class DeployExecutableByteSerializerTest extends AnyFunSuite {
  val serializer = new DeployExecutableByteSerializer

  test("Test serialize ModuleBytes ") {
    val args: DeployNamedArg = new DeployNamedArg("payment", CLValue.U512(BigInt.apply("9999999999")))
    val ModuleBytes = new ModuleBytes(Array.fill[Byte](75)(1), Seq(Seq(args)))
    assert("004b00000001010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101000000070000007061796d656e740600000005ffe30b540208" == HexUtils.toHex(serializer.toBytes(ModuleBytes)).get)

  }

  test("Test serialize storedContractByName ") {
    val args: DeployNamedArg = new DeployNamedArg("quantity", CLValue.I32(1000))
    val storedContractByName = new StoredContractByName("casper-example", "example-entry-point", Seq(Seq(args)))
    assert("020e0000006361737065722d6578616d706c65130000006578616d706c652d656e7472792d706f696e7401000000080000007175616e7469747904000000e803000001" == HexUtils.toHex(serializer.toBytes(storedContractByName)).get)
  }

  test("Test serialize storedContractByHash ") {
    val args: DeployNamedArg = new DeployNamedArg("quantity", CLValue.I32(1000))
    val storedContractByHash = new StoredContractByHash(Some(new Hash("c4c411864f7b717c27839e56f6f1ebe5da3f35ec0043f437324325d65a22afa4")), "pclphXwfYmCmdITj8hnh", Seq(Seq(args)))
    assert("01c4c411864f7b717c27839e56f6f1ebe5da3f35ec0043f437324325d65a22afa41400000070636c7068587766596d436d6449546a38686e6801000000080000007175616e7469747904000000e803000001" == HexUtils.toHex(serializer.toBytes(storedContractByHash)).get)
  }


  test("Test serialize storedVersionedContractByHash ") {
    val args: DeployNamedArg = new DeployNamedArg("test", CLValue.String("Hello, World!"))
    val storedVersionedContractByHash = new StoredVersionedContractByHash(Some(new Hash("b348fdd0d0b3f66468687df93141b5924f6bb957d5893c08b60d5a78d0b9a423")), None, "PsLz5c7JsqT8BK8ll0kF", Seq(Seq(args)))
    assert("03b348fdd0d0b3f66468687df93141b5924f6bb957d5893c08b60d5a78d0b9a42300010000000400000074657374110000000d00000048656c6c6f2c20576f726c64210a" == HexUtils.toHex(serializer.toBytes(storedVersionedContractByHash)).get)
  }

  test("Test serialize StoredVersionedContractByName ") {
    val args: DeployNamedArg = new DeployNamedArg("test", CLValue.String("Hello, World!"))
    val storedVersionedContractByName = new StoredVersionedContractByName("test-contract", None, "PsLz5c7JsqT8BK8ll0kF", Seq(Seq(args)))
    assert("040d000000746573742d636f6e747261637400010000000400000074657374110000000d00000048656c6c6f2c20576f726c64210a" == HexUtils.toHex(serializer.toBytes(storedVersionedContractByName)).get)
  }



  test("Test serialize DeployTransfer ") {
    val args: DeployNamedArg = new DeployNamedArg("amount", CLValue.I32(1000))
    val deployTransfer = new DeployTransfer(Seq(Seq(args)))
    assert("050100000006000000616d6f756e7404000000e803000001" == HexUtils.toHex(serializer.toBytes(deployTransfer)).get)

  }

}
