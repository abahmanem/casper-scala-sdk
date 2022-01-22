package com.casper.sdk.json.serialize

import com.casper.sdk.types.cltypes.CLPublicKey
import com.casper.sdk.util.HexUtils
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.{JsonSerializer, SerializerProvider}
import com.casper.sdk.types.cltypes.Signature

class SignatureSerializer extends JsonSerializer[Signature] {
  override def serialize(value: Signature, gen: JsonGenerator, serializers: SerializerProvider): Unit = {
    require(value != null&&value.bytes!=null)
    
    gen.writeStartObject
    gen.writeFieldName("cl_type")
    gen.getCodec.writeValue(gen, "PublicKey")
    gen.writeFieldName("bytes")
    gen.writeString(HexUtils.toHex(value.bytes).get)
    gen.writeFieldName("parsed")
    gen.writeString(HexUtils.toHex(value.bytes).get)
    gen.writeEndObject

    // gen.writeString(value.formatAsHexAccount)
  }
}