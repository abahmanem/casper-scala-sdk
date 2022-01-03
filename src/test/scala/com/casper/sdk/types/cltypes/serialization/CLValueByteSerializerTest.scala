package com.casper.sdk.types.cltypes.serialization

import org.scalatest.funsuite.AnyFunSuite
import com.casper.sdk.types.cltypes.{CLTypeInfo, CLValue, CLType}
import com.casper.sdk.util.HexUtils
import com.casper.sdk.types.cltypes.serialization.CLValueByteSerializer

class CLValueByteSerializerTest extends AnyFunSuite {

  val cLValueByteSerializer = new CLValueByteSerializer()

  /**
   * Test Bool CLValue
   */

  test("Test  serialize data with true Bool CLValue ") {
    val clValue = CLValue.Bool(true)
    assert("01" == HexUtils.toHex(clValue.bytes))
  }

  test("Test CLValueByteSerializer with true Bool CLType ") {
    val bytes = cLValueByteSerializer.toBytes((CLValue.Bool(false)))
    assert("010000000000" == HexUtils.toHex(bytes))
  }


  test("Test serialize data  Unit CLType ") {
    val clValue = CLValue.Unit()
    assert("" == HexUtils.toHex(clValue.bytes))
  }

  test("Test CLValueByteSerializer with Unit CLType ") {
    val bytes = cLValueByteSerializer.toBytes((CLValue.Unit()))
    assert("0000000009" == HexUtils.toHex(bytes))
  }


  test("Test serialize data  U8 CLType ") {
    val clValue = CLValue.U8(10)
    assert("00000000000000000000" == HexUtils.toHex(clValue.bytes))
  }

  test("Test CLValueByteSerializer with U8 CLType ") {
    val bytes = cLValueByteSerializer.toBytes((CLValue.U8(10)))
    assert("0a0000000000000000000000000003" == HexUtils.toHex(bytes))
  }


  test("Test serialize data String CLType ") {

    val clValue = CLValue.String("Hello, World!")
    assert("0d00000048656c6c6f2c20576f726c6421" == HexUtils.toHex(clValue.bytes))

  }

  test("Test CLValueByteSerializer with String CLType ") {
    val bytes = cLValueByteSerializer.toBytes(CLValue.String("Hello, World!"))
    assert("110000000d00000048656c6c6f2c20576f726c64210a" == HexUtils.toHex(bytes))
  }


  test("Test serialize data  I32 CLType ") {
    val clValue = CLValue.I32(7)
    assert("07000000" == HexUtils.toHex(clValue.bytes))
  }

  test("Test CLValueByteSerializer with I32 CLType ") {
    val bytes = cLValueByteSerializer.toBytes((CLValue.I32(7)))
    assert("040000000700000001" == HexUtils.toHex(bytes))
  }

  test("Test serialize data  U32 CLType ") {
    val clValue = CLValue.U32(1024)
    assert("00040000" == HexUtils.toHex(clValue.bytes))
  }

  test("Test CLValueByteSerializer with U32 CLType ") {
    val bytes = cLValueByteSerializer.toBytes((CLValue.U32(1024)))
    assert("040000000004000004" == HexUtils.toHex(bytes))
  }


  test("Test serialize data  I64 CLType ") {
    val clValue = CLValue.I64(-16)
    assert("f0ffffffffffffff" == HexUtils.toHex(clValue.bytes))
  }

  test("Test CLValueByteSerializer with I64 CLType ") {
    val bytes = cLValueByteSerializer.toBytes((CLValue.I64(-16)))
    assert("08000000f0ffffffffffffff02" == HexUtils.toHex(bytes))
  }

  test("Test serialize data  U64 CLType ") {
    val clValue = CLValue.U64(16)
    assert("1000000000000000" == HexUtils.toHex(clValue.bytes))
  }

  test("Test CLValueByteSerializer with U64 CLType ") {
    val bytes = cLValueByteSerializer.toBytes((CLValue.U64(16)))
    assert("08000000100000000000000005" == HexUtils.toHex(bytes))
  }
  test("Test serialize data  U128 CLType ") {
    val clValue = CLValue.U128(1024)
    assert("020004" == HexUtils.toHex(clValue.bytes))
  }

  test("Test CLValueByteSerializer with U128 CLType ") {
    val bytes = cLValueByteSerializer.toBytes((CLValue.U128(1024)))
    assert("0300000002000406" == HexUtils.toHex(bytes))
  }

  test("Test serialize data  U256 CLType ") {
    val clValue = CLValue.U256(104845)
    assert("038d9901" == HexUtils.toHex(clValue.bytes))
  }

  test("Test CLValueByteSerializer with U256 CLType ") {
    val bytes = cLValueByteSerializer.toBytes((CLValue.U256(104845)))
    assert("04000000038d990107" == HexUtils.toHex(bytes))
  }


  test("Test serialize data  U512 CLType ") {

    val clValue = CLValue.U512(BigInt.apply("123456789101112131415"))
    assert("0957ff1ada959f4eb106" == HexUtils.toHex(clValue.bytes))
  }

  test("Test CLValueByteSerializer with U512 CLType ") {
    val bytes = cLValueByteSerializer.toBytes((CLValue.U512(BigInt.apply("123456789101112131415"))))
    assert("0a0000000957ff1ada959f4eb10608" == HexUtils.toHex(bytes))
  }

  test("Test serialize data None Option CLType ") {

    val clValue = CLValue.OptionNone(new CLTypeInfo(CLType.String))
    assert("00" == HexUtils.toHex(clValue.bytes))
  }

  test("Test CLValueByteSerializer with None Option CLType ") {
    val bytes = cLValueByteSerializer.toBytes((CLValue.OptionNone(new CLTypeInfo(CLType.String))))
    assert("01000000000a" == HexUtils.toHex(bytes))
  }


  test("Test serialize data  Option CLType ") {

    val clValue = CLValue.Option(CLValue.U32(10))
    assert("010a000000" == HexUtils.toHex(clValue.bytes))
  }

  test("Test CLValueByteSerializer with  Option CLType ") {
    val bytes = cLValueByteSerializer.toBytes((CLValue.Option(CLValue.U32(10))))
    assert("05000000010a00000004" == HexUtils.toHex(bytes))
  }

  test("Test serialize Empty List of CLType ") {

    val clValue = CLValue.List()
    assert("00000000" == HexUtils.toHex(clValue.bytes))
  }

  test("Test CLValueBteSerializer with empty List CLType ") {
    val bytes = cLValueByteSerializer.toBytes(CLValue.List())
    assert("040000000000000009" == HexUtils.toHex(bytes))
  }

  test("Test serialize non empty List of CLType ") {
    val clValue = CLValue.List(CLValue.U32(1), CLValue.U32(2), CLValue.U32(3))
    assert("03000000010000000200000003000000" == HexUtils.toHex(clValue.bytes))
  }

  test("Test CLValueBteSerializer with non empty List of CLType ") {
    val bytes = cLValueByteSerializer.toBytes((CLValue.List(CLValue.U32(1), CLValue.U32(2), CLValue.U32(3))))
    assert("100000000300000001000000020000000300000004" == HexUtils.toHex(bytes))
  }


  test("Test serialize Ok Result CLType ") {
    val clValue = CLValue.Ok(CLValue.U64(314), new CLTypeInfo(CLType.String))
    assert("013a01000000000000" == HexUtils.toHex(clValue.bytes))
  }

  test("Test CLValueBteSerializer with Ok Result CLType ") {
    val bytes = cLValueByteSerializer.toBytes((CLValue.Ok(CLValue.U64(314), new CLTypeInfo(CLType.String))))
    assert("09000000013a01000000000000050a" == HexUtils.toHex(bytes))
  }


  test("Test serialize Err Result CLType ") {
    val clValue = CLValue.Err(CLValue.String("Uh oh"), new CLTypeInfo(CLType.String))
    assert("00050000005568206f68" == HexUtils.toHex(clValue.bytes))
  }

  test("Test CLValueBteSerializer with Err Result CLType ") {
    val bytes = cLValueByteSerializer.toBytes((CLValue.Err(CLValue.String("Uh oh"), new CLTypeInfo(CLType.String))))
    assert("0a00000000050000005568206f680a0a" == HexUtils.toHex(bytes))
  }

  test("Test serialize Tuple1  CLType ") {
    val clValue = CLValue.Tuple1(CLValue.String("Hello, World!"))
    assert("0d00000048656c6c6f2c20576f726c6421" == HexUtils.toHex(clValue.bytes))
  }

  test("Test CLValueBteSerializer with Tuple1 CLType ") {
    val bytes = cLValueByteSerializer.toBytes((CLValue.Tuple1(CLValue.String("Hello, World!"))))
    assert("110000000d00000048656c6c6f2c20576f726c64210a" == HexUtils.toHex(bytes))
  }


  test("Test serialize Tuple2  CLType ") {
    val clValue = CLValue.Tuple2(CLValue.U32(1), CLValue.String("Hello, World!"))
    assert("010000000d00000048656c6c6f2c20576f726c6421" == HexUtils.toHex(clValue.bytes))
  }

  test("Test CLValueBteSerializer with Tuple2 CLType ") {
    val bytes = cLValueByteSerializer.toBytes((CLValue.Tuple2(CLValue.U32(1), CLValue.String("Hello, World!"))))
    assert("15000000010000000d00000048656c6c6f2c20576f726c6421040a" == HexUtils.toHex(bytes))
  }


  test("Test serialize Tuple3  CLType ") {
    val clValue = CLValue.Tuple3(CLValue.U32(1), CLValue.String("Hello, World!"), CLValue.Bool(true))
    assert("010000000d00000048656c6c6f2c20576f726c642101" == HexUtils.toHex(clValue.bytes))
  }

  test("Test CLValueBteSerializer with Tuple3 CLType ") {
    val bytes = cLValueByteSerializer.toBytes((CLValue.Tuple3(CLValue.U32(1), CLValue.String("Hello, World!"), CLValue.Bool(true))))
    assert("16000000010000000d00000048656c6c6f2c20576f726c642101040a00" == HexUtils.toHex(bytes))
  }

  test("Test serialize ByteArray  CLType ") {
    val bytes = Array[Byte](1, -39, -65, 33, 72, 116, -118, -123, -56, -99, -91, -86, -40, -18, 11, 15, -62, -47, 5, -3, 57, -44, 26, 76, 121, 101, 54, 53, 79, 10, -30, -112, 12)
    val clValue = CLValue.ByteArray(bytes)
    assert("01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900c" == HexUtils.toHex(clValue.bytes))
  }

  test("Test CLValueBteSerializer with ByteArray CLType ") {
    val bytes = Array[Byte](1, -39, -65, 33, 72, 116, -118, -123, -56, -99, -91, -86, -40, -18, 11, 15, -62, -47, 5, -3, 57, -44, 26, 76, 121, 101, 54, 53, 79, 10, -30, -112, 12)
    val clValue = CLValue.ByteArray(bytes)
    assert("01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900c" == HexUtils.toHex(clValue.bytes))
  }
  //TODO add map
}
