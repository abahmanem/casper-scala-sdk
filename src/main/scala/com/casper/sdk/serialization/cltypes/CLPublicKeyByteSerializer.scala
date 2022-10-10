package com.casper.sdk.serialization.cltypes

import com.casper.sdk.serialization.BytesSerializable
import com.casper.sdk.types.cltypes.CLPublicKey

import scala.collection.mutable.ArrayBuilder
import scala.util.{Failure, Success, Try}
/**
 * CLPublicKeySerializer
 */
class CLPublicKeyByteSerializer extends BytesSerializable[CLPublicKey] {
  //def toBytes(value: CLPublicKey): Option[Array[Byte]] = Try(value.bytes).toOption
 def toBytes(value: CLPublicKey): Option[Array[Byte]] = Try(value.formatAsByteAccount).toOption
}