package com.casper.sdk.json.deserialize

import com.casper.sdk.types.cltypes.{CLPublicKey, CLType, CLValue,CLOptionTypeInfo}
import com.casper.sdk.util.{HexUtils, JsonConverter}
import org.scalatest.funsuite.AnyFunSuite

/**
 * CLValueSerializerTest
 */
class CLValueDeSerializerTest extends AnyFunSuite {


  /**
   * Test deserialize  U512 CLValue
   */
  test("Deserialize U512 value") {
    val u512 =
      """  {
        |                                "cl_type": "U512",
        |                                "bytes": "0400ca9A3B",
        |                                "parsed": "1000000000"
        |                            }""".stripMargin

    val value = JsonConverter.fromJson[CLValue](u512).get
    info("value is not null")
    assert(value != null)
    info("parsed value = 1000000000 ")
    assert(value.parsed == "1000000000")
    info("cl_type = U512 ")
    assert(value.cl_infoType.cl_Type == CLType.U512)
    info("bytes same as  HexUtils.fromHex(\"0400ca9A3B\") ")
    assert(value.bytes.sameElements(HexUtils.fromHex("0400ca9A3B").get))
  }

  /**
   * Test deserialize  PublicKey CLValue
   */
  test("Deserialize PublicKey value") {
    val pubKey =
      """ {
        |                                "cl_type": "PublicKey",
        |                                "bytes": "017d96b9A63ABCB61C870a4f55187A0a7AC24096Bdb5Fc585c12a686a4D892009e",
        |                                "parsed": "017d96B9A63abCB61c870A4F55187a0a7AC24096bdB5fc585C12A686a4D892009e"
        |                            }""".stripMargin


    val value = JsonConverter.fromJson[CLValue](pubKey).get
    info("value is not null")
    assert(value != null)
    info("parsed value = 017d96B9A63abCB61c870A4F55187a0a7AC24096bdB5fc585C12A686a4D892009e ")
    assert(value.parsed == "017d96B9A63abCB61c870A4F55187a0a7AC24096bdB5fc585C12A686a4D892009e")
    info("cl_type = PublicKey ")
    assert(value.cl_infoType.cl_Type == CLType.PublicKey)
    info("bytes same as  HexUtils.fromHex(\"017d96B9A63abCB61c870A4F55187a0a7AC24096bdB5fc585C12A686a4D892009e\") ")
    assert(value.bytes.sameElements(HexUtils.fromHex("017d96B9A63abCB61c870A4F55187a0a7AC24096bdB5fc585C12A686a4D892009e").get))
  }

  /**
   * Test deserialize  byteArray32 CLValue
   */

  test("Deserialize byteArray32 value") {
    val byteArray32 =
      """ {
        |                                "cl_type": {
        |                                    "ByteArray": 32
        |                                },
        |                                "bytes": "2fe0d35b6a92e17ee8f3ee3693452d6141df5c8db8e17a1c0985572842e13385",
        |                                "parsed": "2fe0d35b6a92e17ee8f3ee3693452d6141df5c8db8e17a1c0985572842e13385"
        |                            }""".stripMargin
    val value = JsonConverter.fromJson[CLValue](byteArray32).get
    info("value is not null")
    assert(value != null)
    info("parsed value = 2fe0d35b6a92e17ee8f3ee3693452d6141df5c8db8e17a1c0985572842e13385 ")
    assert(value.parsed == "2fe0d35b6a92e17ee8f3ee3693452d6141df5c8db8e17a1c0985572842e13385")
    info("cl_type = ByteArray ")
    assert(value.cl_infoType.cl_Type == CLType.ByteArray)
    info("bytes same as  HexUtils.fromHex(\"2fe0d35b6a92e17ee8f3ee3693452d6141df5c8db8e17a1c0985572842e13385\") ")
    assert(value.bytes.sameElements(HexUtils.fromHex("2fe0d35b6a92e17ee8f3ee3693452d6141df5c8db8e17a1c0985572842e13385").get))

  }

  /**
   * Test deserialize  Option CLValue
   */
  test("Deserialize Option value") {
    val option =
      """ {
        |                                "cl_type": {
        |                                    "Option": "U64"
        |                                },
        |                                "bytes": "00",
        |                                "parsed": null
        |                            }""".stripMargin
    val value = JsonConverter.fromJson[CLValue](option).get
    info("value is not null")
    assert(value != null)
    info("parsed value = null")
    assert(value.parsed == null)
    info("cl_type = Option ")
    assert(value.cl_infoType.cl_Type == CLType.Option)
    info("inner cltype = U64 ")
    assert(value.cl_infoType.asInstanceOf[CLOptionTypeInfo].inner.get.cl_Type == CLType.U64)

    info("bytes same as  HexUtils.fromHex(\"00\") ")
    assert(value.bytes.sameElements(HexUtils.fromHex("00").get))
  }

  /**
   * Test deserialize  String CLValue
   */

  test("Deserialize String value") {

    val str =
      """ {
        |                                "cl_type": "String",
        |                                "bytes": "1a00000068747470733a2f2f636173706572636f6d6d756e6974792e696f",
        |                                "parsed": "https://caspercommunity.io"
        |                            }""".stripMargin

    val value = JsonConverter.fromJson[CLValue](str).get
    info("value is not null")
    assert(value != null)
    info("parsed value = https://caspercommunity.io")
    assert(value.parsed == "https://caspercommunity.io")
    info("cl_type = String ")
    assert(value.cl_infoType.cl_Type == CLType.String)
    info("bytes same as  HexUtils.fromHex(\"1a00000068747470733a2f2f636173706572636f6d6d756e6974792e696f\") ")
    assert(value.bytes.sameElements(HexUtils.fromHex("1a00000068747470733a2f2f636173706572636f6d6d756e6974792e696f").get))
  }

}