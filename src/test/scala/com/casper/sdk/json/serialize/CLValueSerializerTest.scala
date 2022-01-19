package com.casper.sdk.json.serialize

import com.casper.sdk.types.cltypes.{CLType, CLValue}
import com.casper.sdk.util.{HexUtils, JsonConverter}
import org.scalatest.funsuite.AnyFunSuite
/**
 * CLValue Custom Json serializer
 */

class CLValueSerializerTest extends AnyFunSuite {


  test("Serialize Option value") {
    val json = """{
                 |  "cl_type" : {
                 |    "Option" : "String"
                 |  },
                 |  "bytes" : "010d00000048656c6c6f2c20576f726c6421",
                 |  "parsed" : "Hello, World!"
                 |}""".stripMargin
    val v = CLValue.Option(CLValue.String("Hello, World!"))
    info("CLValue.Option(CLValue.String(\"Hello, World!\")) serializes to "+json)
    assert(JsonConverter.toJson(v)==json)
  }


  test("Serialize Bool value") {
    val json = """{
                 |  "cl_type" : "Bool",
                 |  "bytes" : "01",
                 |  "parsed" : "true"
                 |}""".stripMargin
    val v = CLValue.Bool(true)
    info("CLValue.Bool(true) serializes to "+json)
    assert(JsonConverter.toJson(v)==json)
  }

  test("Serialize String value") {
    val json = """{
                 |  "cl_type" : "String",
                 |  "bytes" : "0d00000048656c6c6f2c20776f726c6421",
                 |  "parsed" : "Hello, world!"
                 |}""".stripMargin
    val v = CLValue.String("Hello, world!")
    info("CLValue.String(\"Hello, world!\") serializes to "+json)
    assert(JsonConverter.toJson(v)==json)
  }


  test("Serialize U8 value") {
    val json = """{
                 |  "cl_type" : "U8",
                 |  "bytes" : "00000000000000",
                 |  "parsed" : 7
                 |}""".stripMargin
    val v = CLValue.U8(7)
    info("CLValue.U8(7) serializes to "+json)
    assert(JsonConverter.toJson(v)==json)
  }

  test("Serialize U512 value") {
    val json = """{
                 |  "cl_type" : "U512",
                 |  "bytes" : "0412e8571b",
                 |  "parsed" : 458745874
                 |}""".stripMargin
    val v = CLValue.U512(458745874)
    info("CLValue.U512(458745874) serializes to "+json)
    assert(JsonConverter.toJson(v)==json)
  }

  test("Serialize U32 value") {
    val json = """{
                 |  "cl_type" : "U32",
                 |  "bytes" : "32b30000",
                 |  "parsed" : 45874
                 |}""".stripMargin
    val v = CLValue.U32(45874)
    info("CLValue.U32(45874) serializes to "+json)
    assert(JsonConverter.toJson(v)==json)
  }

  test("Serialize I32 value") {
    val json = """{
                 |  "cl_type" : "I32",
                 |  "bytes" : "10270000",
                 |  "parsed" : 10000
                 |}""".stripMargin
    val v = CLValue.I32(10000)
    info("CLValue.I32(10000) serializes to "+json)
    assert(JsonConverter.toJson(v)==json)
  }

  test("Serialize I64 value") {
    val json = """{
                 |  "cl_type" : "I64",
                 |  "bytes" : "99bdf7ffffffffff",
                 |  "parsed" : -541287
                 |}""".stripMargin
    val v = CLValue.I64(-541287)
    info("CLValue.I64(-541287) serializes to "+json)
    assert(JsonConverter.toJson(v)==json)
  }

  test("Serialize U128 value") {
    val json = """{
                 |  "cl_type" : "U128",
                 |  "bytes" : "04bf3d3209",
                 |  "parsed" : 154287551
                 |}""".stripMargin
    val v = CLValue.U128(154287551)
    info("CLValue.U128(458745874) serializes to "+json)
    assert(JsonConverter.toJson(v)==json)
  }

  test("Serialize U256 value") {
    val json = """{
                 |  "cl_type" : "U256",
                 |  "bytes" : "0a2e1a2022a7d14f69f600",
                 |  "parsed" : 4545487556545454545454
                 |}""".stripMargin
    val v = CLValue.U256(BigInt.apply("4545487556545454545454"))
    info("CLValue.U256(BigInt.apply(\"4545487556545454545454\")) serializes to "+json)
    assert(JsonConverter.toJson(v)==json)
  }

}