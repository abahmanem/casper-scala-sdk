package com.casper.sdk.types.cltypes.serialization

import java.nio.ByteBuffer

class ArbitraryWidthNumberSerializer(maxBytes: Int, signed: Boolean) extends CLTypeSerializer[BigInt] {
  override def encode(value: BigInt): Array[Byte] = {
   null
  }

}
