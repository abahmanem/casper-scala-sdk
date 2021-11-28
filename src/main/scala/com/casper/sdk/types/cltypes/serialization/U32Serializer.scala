package com.casper.sdk.types.cltypes.serialization

class U32Serializer extends CLTypeSerializer[Int] {
  override def encode(value: Int): Array[Byte] = {
  new Array[Byte](value)
  }
}