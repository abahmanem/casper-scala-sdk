package com.casper.sdk.json.serialize

import com.casper.sdk.types.cltypes.{CLType, CLTypeInfo, CLValue}
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



  test("Serialize List of CLvalues") {
    val json = """{
                 |  "cl_type" : {
                 |    "List" : "String"
                 |  },
                 |  "bytes" : "030000000300000061626304000000646566670a00000068696a6b6c6d6e6f7071",
                 |  "parsed" : "[\"abc\",\"defg\",\"hijklmnopq\"]"
                 |}""".stripMargin
    val v = CLValue.List(CLValue.String("abc"),CLValue.String("defg") , CLValue.String("hijklmnopq"))
    info("CLValue.List(CLValue.String(\"abc\"),CLValue.String(\"defg\") , CLValue.String(\"hijklmnopq\")) serializes to "+json)
    assert(JsonConverter.toJson(v)==json)
  }


  test("Serialize Tuple1 with CLValue") {
    val json = """{
                 |  "cl_type" : {
                 |    "Tuple1" : [
                 |      "String"
                 |    ]
                 |  },
                 |  "bytes" : "050000006162636566",
                 |  "parsed" : "[\"abcef\"]"
                 |}""".stripMargin
    val v = CLValue.Tuple1(CLValue.String("abcef"))
    info("CLValue.Tuple1(CLValue.String(\"abcef\")) serializes to "+json)
    assert(JsonConverter.toJson(v)==json)
  }


  test("Serialize Tuple2 with CLValues") {
    val json = """{
                 |  "cl_type" : {
                 |    "Tuple2" : [
                 |      "String",
                 |      "U512"
                 |    ]
                 |  },
                 |  "bytes" : "030000006162630102",
                 |  "parsed" : "[\"abc\",\"2\"]"
                 |}""".stripMargin
    val v = CLValue.Tuple2(CLValue.String("abc"), CLValue.U512(2))
    info("CLValue.Tuple2(CLValue.String(\"abc\"), CLValue.U512(2)) serializes to "+json)
    assert(JsonConverter.toJson(v)==json)
  }


  test("Serialize Tuple3 with CLValues") {
    val json = """{
                 |  "cl_type" : {
                 |    "Tuple3" : [
                 |      "PublicKey",
                 |      {
                 |        "Option" : "String"
                 |      },
                 |      "U512"
                 |    ]
                 |  },
                 |  "bytes" : "01a018bf278f32fdb7b06226071ce399713ace78a28d43a346055060a660ba7aa901030000006162630102",
                 |  "parsed" : "[\"01a018bf278f32fdb7b06226071ce399713ace78a28d43a346055060a660ba7aa9\",\"abc\",\"2\"]"
                 |}""".stripMargin
    val v = CLValue.Tuple3(CLValue.PublicKey("01a018bf278f32fdb7b06226071ce399713ace78a28d43a346055060a660ba7aa9") ,

      CLValue.Option(CLValue.String("abc")), CLValue.U512(2))
    info("CLValue.Tuple3(CLValue.PublicKey(\"01a018bf278f32fdb7b06226071ce399713ace78a28d43a346055060a660ba7aa9\") ,\n\n    CLValue.Option(CLValue.String(\"abc\")), CLValue.U512(2)) serializes to "+json)
    assert(JsonConverter.toJson(v)==json)
  }

  test("Serialize Result with OK CLValue") {
    val json = """{
                 |  "cl_type" : {
                 |    "Result" : {
                 |      "ok" : "String",
                 |      "err" : "String"
                 |    }
                 |  },
                 |  "bytes" : "010a000000676f6f64726573756c74",
                 |  "parsed" : "goodresult"
                 |}""".stripMargin
    val v = CLValue.Ok(CLValue.String("goodresult"),new CLTypeInfo (CLType.String))
    info("CLValue.Ok(CLValue.String(\"goodresult\"),new CLTypeInfo (CLType.String)) serializes to "+json)
    assert(JsonConverter.toJson(v)==json)
  }

  test("Serialize Result with Err CLValue") {
    val json = """{
                 |  "cl_type" : {
                 |    "Result" : {
                 |      "ok" : "String",
                 |      "err" : "String"
                 |    }
                 |  },
                 |  "bytes" : "001a00000075682c20736f6d657468696e6720776e65742077726f6e672121",
                 |  "parsed" : "uh, something wnet wrong!!"
                 |}""".stripMargin
    val v = CLValue.Err(CLValue.String("uh, something wnet wrong!!"),new CLTypeInfo (CLType.String))
    info("CLValue.Err(CLValue.String(\"uh, something wnet wrong!!\"),new CLTypeInfo (CLType.String)) serializes to "+json)
    assert(JsonConverter.toJson(v)==json)
  }




}