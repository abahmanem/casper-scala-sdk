package com.casper.sdk.types.cltypes.serialization

import java.nio.ByteBuffer

class FixedWitdhNumberSerializer(maxBytes: Int, signed: Boolean) extends CLTypeSerializer[BigInt] {

  override def encode(value: BigInt): Array[Byte] = {
  null
  }


}
