package com.casper.sdk.types.cltypes

class CLOptionValue(
                     override val bytes: Array[Byte],
                     val clTypeOption: CLTypeInfo,
                     override val parsed: Any
                   )
  extends CLValue(bytes,clTypeOption,parsed) {
  def this(hexBytes : String, clTypeOption: CLTypeInfo , parsed : Any) =   this(hexBytes.getBytes(), clTypeOption, parsed)
}
