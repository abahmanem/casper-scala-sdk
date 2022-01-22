package com.casper.sdk.serialization.cltypes

import com.casper.sdk.serialization.BytesSerializable
import com.casper.sdk.types.cltypes.CLPublicKey

import scala.collection.mutable.ArrayBuilder

/**
 * CLPublicKeySerializer
 */
class CLPublicKeySerializer extends BytesSerializable[CLPublicKey] {

  def toBytes(value: CLPublicKey): Array[Byte] = {
    require(value != null)
    value.formatAsByteAccount
  }
}