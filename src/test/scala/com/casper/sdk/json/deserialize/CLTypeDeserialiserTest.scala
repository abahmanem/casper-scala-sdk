package com.casper.sdk.json.deserialize
import com.casper.sdk.types.cltypes.CLType
import com.casper.sdk.util.JsonConverter
import org.scalatest.funsuite.AnyFunSuite

/**
 * CLTypeDeserialiserTest
 */
class CLTypeDeserialiserTest  extends AnyFunSuite {

  /**
   * Test  Deserialize U512 CLType
   */

  test("Deserialize  U512 CLType") {
     val u512 = """"U512""""
     val cltype = JsonConverter.fromJson[CLType](u512).get
     assert(cltype == CLType.U512)
   }

  /**
   * Test  Deserialize ByteArray CLType
   */

  test("Deserialize  ByteArray CLType") {
    val byteArray = """{
                      |                               "ByteArray": 32
                      |                                }""".stripMargin
    val cltype = JsonConverter.fromJson[CLType](byteArray).get
    assert(cltype == CLType.ByteArray)
  }

  /**
   * Test  Deserialize Option CLType
   */

  test("Deserialize  Option CLType") {
    val option = """{
                      |                               "Option": "U64"
                      |                                }""".stripMargin
    val cltype = JsonConverter.fromJson[CLType](option).get
    assert(cltype == CLType.Option)
  }

}