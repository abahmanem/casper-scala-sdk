package com.casper.sdk.json.deserialize

import com.casper.sdk.types.cltypes.AccountHash
import com.casper.sdk.util.JsonConverter
import org.scalatest.funsuite.AnyFunSuite

/**
 * AccountHashDeserializerTest
 */
class AccountHashDeserializerTest extends AnyFunSuite {
  test("Deserialize AccountHash") {
    val jsonkey = """ "account-hash-85148dcd6c54b77e462a9acf387fb05aca953a83011db2c601716de0af1cf47c" """
    val account = JsonConverter.fromJson[AccountHash](jsonkey).get
    info("AccountHash is not null")
    assert(account != null)
    info("account.bytes  is the same as new AccountHash(\"account-hash-85148dcd6c54b77e462a9acf387fb05aca953a83011db2c601716de0af1cf47c\").bytes")
    val bytes= AccountHash("account-hash-85148dcd6c54b77e462a9acf387fb05aca953a83011db2c601716de0af1cf47c").get.bytes
    assert(account.bytes.sameElements(bytes))
  }
}