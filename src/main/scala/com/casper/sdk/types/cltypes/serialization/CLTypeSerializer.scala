package com.casper.sdk.types.cltypes.serialization

trait CLTypeSerializer[T] {
   def encode(value: T) :  Array[Byte]
}
