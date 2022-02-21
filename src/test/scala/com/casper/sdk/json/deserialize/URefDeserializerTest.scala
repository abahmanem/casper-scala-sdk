package com.casper.sdk.json.deserialize

import com.casper.sdk.types.cltypes.CLValue
import com.casper.sdk.types.cltypes.URef
import com.casper.sdk.util.JsonConverter
import org.scalatest.funsuite.AnyFunSuite

/**
 * URefDeserializerTest
 */
class URefDeserializerTest extends AnyFunSuite {
  test("Deserialize URef") {
    val jsonuref = """ "uref-9cC68775d07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d067C4D0363-007" """
    val uref = JsonConverter.fromJson[URef](jsonuref).get
    info("URef is not null")
    assert(uref != null)
    info("uref.bytes  is the same as new Uref(\"uref-9cC68775d07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d067C4D0363-007\").bytes")
    assert(uref.bytes.sameElements(URef("uref-9cC68775d07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d067C4D0363-007").get.bytes))
  }
}