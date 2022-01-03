package com.casper.sdk.types.cltypes.serialization

import scala.collection.mutable.ArrayBuilder
import com.casper.sdk.types.cltypes.CLPublicKey

/**
 * CLPublicKeySerializer
 */
class CLPublicKeySerializer extends BytesSerializable[CLPublicKey] {

  def toBytes(value: CLPublicKey): Array[Byte] = {
    assert(value != null)
    value.formatAsByteAccount
  }
}