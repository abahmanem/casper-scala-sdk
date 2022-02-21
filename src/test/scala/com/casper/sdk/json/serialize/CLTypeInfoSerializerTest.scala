package com.casper.sdk.json.serialize

import com.casper.sdk.types.cltypes.{CLByteArrayTypeInfo, CLOptionTypeInfo, CLPublicKey, CLType, CLTypeInfo}
import com.casper.sdk.util.JsonConverter
import org.scalatest.funsuite.AnyFunSuite
/**
 * 
 *  CLTypeInfo Custom Json serializer
 */

class CLTypeInfoSerializerTest extends AnyFunSuite {

  test("Serialize CLTypeInfo with U64") {
    val json = """"U64""""
    val cLTypeInfo =  new CLTypeInfo(CLType.U64)
    info("new  CLTypeInfo(CLType.U64) json serialization =  " + json )
    assert(JsonConverter.toJson(cLTypeInfo).get == json)
  }

  test("Serialize CLTypeInfo with String") {
    val json = """"String""""
    val cLTypeInfo =  new CLTypeInfo(CLType.String)
    info("new  CLTypeInfo(CLType.String) json serialization =  " + json )
    assert(JsonConverter.toJson(cLTypeInfo).get == json)
  }


  test("Serialize CLTypeInfo with ByteArray") {
    val json = """{
                 |  "ByteArray" : 124
                 |}""".stripMargin
    val cLTypeInfo =  new CLByteArrayTypeInfo(124)
    info("new  new CLByteArrayTypeInfo(124) json serialization =  " + json )
    assert(JsonConverter.toJson(cLTypeInfo).get == json)
  }


  test("Serialize CLTypeInfo with Option") {
    val json = """{
                 |  "Option" : "I64"
                 |}""".stripMargin
    val cLTypeInfo =  new CLOptionTypeInfo(new CLTypeInfo(CLType.I64))
    info("new  new CLOptionTypeInfo(new CLTypeInfo(CLType.I64)) json serialization =  " + json )
    assert(JsonConverter.toJson(cLTypeInfo).get == json)
  }
  
}