package com.casper.sdk.domain.deploy

import com.casper.sdk.json.deserialize.HashDeserializer
import com.casper.sdk.json.serialize.HashSerializer
import com.casper.sdk.util.HexUtils
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
/**
 * Hash class
 *
 * @param hash
 */
@JsonSerialize(`using` = classOf[HashSerializer])
@JsonDeserialize(`using` = classOf[HashDeserializer])
case class Hash(
                 hash: Array[Byte]
               ) {
  require(hash.length == 32)

  /**
   * constructor with hex string
   *
   * @param stringHash
   */
  def this(stringHash: String) = this(HexUtils.fromHex(stringHash))
}
