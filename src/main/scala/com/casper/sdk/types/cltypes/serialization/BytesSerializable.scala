package com.casper.sdk.types.cltypes.serialization

/**
 * Trait to serialize casper objects into array bytes
 * @tparam T : Casper object Type
 */
trait BytesSerializable[T] {
  def toBytes(value: T): Array[Byte]
}
