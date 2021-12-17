package com.casper.sdk.util


/**
 * Bytes utility object
 */
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
