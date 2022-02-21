package com.casper.sdk.json.serialize

import com.casper.sdk.domain.deploy.DeployNamedArg
import com.casper.sdk.types.cltypes.CLValue
import com.casper.sdk.util.JsonConverter
import org.scalatest.funsuite.AnyFunSuite
/**
 * DeployNamedArg Custom Json serializer
 */

class DeployNamedArgSerializerTest extends AnyFunSuite {

  test("DeployNamedArgSerializerTest with a single DeployNamedArg "){
    val json = """[
                 |  "amount",
                 |  {
                 |    "cl_type" : "I32",
                 |    "bytes" : "e8030000",
                 |    "parsed" : "1000"
                 |  }
                 |]""".stripMargin

    val arg1 =  new DeployNamedArg("amount", CLValue.I32(1000) )
    assert(JsonConverter.toJson(arg1).get==json)
  }

  test("DeployNamedArgSerializerTest with a list of DeployNamedArg"){
    val json = """[
                 |  [
                 |    [
                 |      "amount",
                 |      {
                 |        "cl_type" : "I32",
                 |        "bytes" : "e8030000",
                 |        "parsed" : "1000"
                 |      }
                 |    ],
                 |    [
                 |      "amount",
                 |      {
                 |        "cl_type" : "String",
                 |        "bytes" : "0400000074657374",
                 |        "parsed" : "test"
                 |      }
                 |    ]
                 |  ]
                 |]""".stripMargin

    val arg1 =  new DeployNamedArg("amount", CLValue.I32(1000) )
    val arg2 =  new DeployNamedArg("amount", CLValue.String("test") )
    assert(JsonConverter.toJson(Seq(Seq(arg1,arg2))).get==json)
  }


}