package com.casper.sdk.types.cltypes.serialization

import java.nio.ByteBuffer

/**
 * 
 * @param maxBytes
 * @param signed
 */
class ArbitraryWidthNumberSerializer(maxBytes: Int, signed: Boolean) extends CLTypeSerializer[BigInt] {
  override def encode(value: BigInt): Array[Byte] = {
   null
  }

}
