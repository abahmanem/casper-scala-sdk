package com.casper.sdk.types.cltypes

/**
 * CLOptionValue
 * @param bytes
 * @param clTypeOption
 * @param parsed
 */
class CLOptionValue(
                     override val bytes: Array[Byte],
                     val clTypeOption: CLTypeInfo,
                     override val parsed: Any
                   )
  extends CLValue(bytes,clTypeOption,parsed) {

  /**
   * Constructor using hex string, CLTypeInfo and a parsed value
   * @param hexBytes
   * @param clTypeOption
   * @param parsed
   */
  def this(hexBytes : String, clTypeOption: CLTypeInfo , parsed : Any) =   this(hexBytes.getBytes(), clTypeOption, parsed)
}
