package com.casper.sdk.json.deserialize

import com.casper.sdk.types.cltypes.Signature
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}

import java.io.IOException

/**
 * Custom fasterXml Deserializer for Signature objects
 *
 */

class SignatureDeserializer extends JsonDeserializer[Signature] {
  @throws[IOException]
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): Signature = {
    new Signature(parser.getText)
  }
}

