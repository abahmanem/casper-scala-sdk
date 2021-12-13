package com.casper.sdk.json.deserialize
import com.casper.sdk.types.cltypes.CLPublicKey
import com.casper.sdk.util.JsonConverter
import org.scalatest.funsuite.AnyFunSuite
class CLTypeDeserialiserTest  extends AnyFunSuite {


  val key = "\"01c6d11a0fa563f8cc3ed5e967d5901c80004bdcde6250ddea18af2b4eae0a902d\""

  /**
   * Test  Deserialize CLPublicKey
   */

  test("Deserialize CLPublicKey") {
    val pubKey = JsonConverter.fromJson[CLPublicKey](key)
    info("key is not null")
    assert(pubKey!=null)
    // assert(key.bytes == new CLPublicKey(JSON).bytes)
  }

}