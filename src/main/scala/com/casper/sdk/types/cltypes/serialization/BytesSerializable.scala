package com.casper.sdk.types.cltypes.serialization

trait BytesSerializable[T] {
  def toBytes(value: T): Array[Byte]
}
