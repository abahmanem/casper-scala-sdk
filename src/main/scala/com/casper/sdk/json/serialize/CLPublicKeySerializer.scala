package com.casper.sdk.json.serialize

import com.casper.sdk.types.cltypes.CLPublicKey
import com.casper.sdk.util.HexUtils
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.{JsonSerializer, SerializerProvider}

/**
 *  CLPublicKey Custom Json serializer
 */
class CLPublicKeySerializer extends JsonSerializer[CLPublicKey] {
  override def serialize(value: CLPublicKey, gen: JsonGenerator, serializers: SerializerProvider): Unit = {
    require(value != null&&value.bytes!=null)
    gen.writeString(value.formatAsHexAccount.get)
  }
}