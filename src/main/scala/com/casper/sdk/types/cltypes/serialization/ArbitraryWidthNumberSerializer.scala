package com.casper.sdk.types.cltypes.serialization

import java.nio.ByteBuffer

class FixedWitdhNumberSerializer(maxBytes: Int, signed: Boolean) extends CLTypeSerializer[BigInt] {

  override def encode(value: BigInt): Array[Byte] = {

    val buffer: ByteBuffer = ByteBuffer.allocate(maxBytes)
  //  buffer .putInt(value)
    //little endian
  //  val bytes = buffer.array.reverse
 //value.signum

  val bytes : Array[Byte] = value.toByteArray
    //little endian
    bytes.reverse
  }


}
