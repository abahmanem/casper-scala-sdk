package com.casper.sdk.json.serialize

import com.casper.sdk.types.cltypes.CLPublicKey
import com.casper.sdk.util.JsonConverter
import org.scalatest.funsuite.AnyFunSuite
/**
 *  CLPublicKey Custom Json serializer
 */
class CLPublicKeySerializerTest extends AnyFunSuite {

  test("Serialize CLPublicKey") {
    val json = """"017d96b9a63abcb61c870a4f55187a0a7ac24096bdb5fc585c12a686a4d892009e""""
    val pubkey =  CLPublicKey("017d96b9A63ABCB61C870a4f55187A0a7AC24096Bdb5Fc585c12a686a4D892009e")
    info("new CLPublicKey(\"017d96b9A63ABCB61C870a4f55187A0a7AC24096Bdb5Fc585c12a686a4D892009e\") json serialization =  " + json )
    assert(JsonConverter.toJson(pubkey).get == json)
    }
}