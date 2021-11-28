package com.casper.sdk.types.cltypes.serialization

trait CLTypeEncoder[T] {
   def encode(value: T) :  Array[Byte]
}
