package com.casper.sdk.json.serialize

import com.casper.sdk.types.cltypes.CLPublicKey
import com.casper.sdk.util.HexUtils
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.{JsonSerializer, SerializerProvider}
import com.casper.sdk.types.cltypes.Signature

class SignatureSerializer extends JsonSerializer[Signature] {
  override def serialize(value: Signature, gen: JsonGenerator, serializers: SerializerProvider): Unit = {
    assert(value != null&&value.bytes!=null)

    assert(value != null)
    gen.writeStartObject
    gen.writeFieldName("cl_type")
    gen.getCodec.writeValue(gen, "PublicKey")
    gen.writeFieldName("bytes")
    gen.writeString(HexUtils.toHex(value.bytes))
    gen.writeFieldName("parsed")
    gen.writeString(HexUtils.toHex(value.bytes))
    gen.writeEndObject

    // gen.writeString(value.formatAsHexAccount)
  }
}