package com.casper.sdk.types.cltypes

import com.casper.sdk.json.deserialize.CLValueSerializer
import com.fasterxml.jackson.core.TreeNode

import java.nio.ByteBuffer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(`using` = classOf[CLValueSerializer])
class CLValue(
               val bytes: Array[Byte],
               val cl_type: CLTypeInfo,
               val parsed: Any
             ) {

  def this(bytes: Array[Byte], cl_Type: CLType) = this(bytes, CLTypeInfo(cl_Type), null)

  def this(hexBytes: String, clType: CLTypeInfo, parsed: Any) = this(hexBytes.getBytes, clType, parsed)

}
