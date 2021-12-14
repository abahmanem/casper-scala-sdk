package com.casper.sdk.util

import org.apache.commons.codec.DecoderException
import org.apache.commons.codec.binary.Hex

object ByteUtils {
  /**
   * concat byte arrays
   * @param parts
   * @return
   */
  def join(parts : Array[Byte]*) : Array[Byte] ={
    assert(parts!=null)
    parts.toArray.flatten
  }
}
