package com.casper.sdk.types.cltypes

import com.casper.sdk.json.deserialize.CLValueSerializer
import com.casper.sdk.util.HexUtils
import com.fasterxml.jackson.core.TreeNode

import java.nio.ByteBuffer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

/**
 * CLValue : datas used by smart contracts.
 *
 * @param bytes
 * @param cl_type
 * @param parsed
 */
@JsonDeserialize(`using` = classOf[CLValueSerializer])
class CLValue(
               val bytes: Array[Byte],
               val cl_infoType: CLTypeInfo,
               val parsed: Any
             ) {

  /**
   * Constructor using a Byte Array and a cltype
   *
   * @param bytes
   * @param cl_Type
   */
  def this(bytes: Array[Byte], cl_Type: CLType) = this(bytes, CLTypeInfo(cl_Type), null)

  /**
   * Constructor using a hex String, cltype and a parsed value
   *
   * @param hexBytes
   * @param clType
   * @param parsed
   */
  def this(hexBytes: String, clType: CLTypeInfo, parsed: Any) = this(HexUtils.hex2Bytes(hexBytes), clType, parsed)

}
