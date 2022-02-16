package com.casper.sdk.json.deserialize

import com.casper.sdk.types.cltypes.{CLPublicKey, CLValue}
import com.fasterxml.jackson.core.{JsonParser, ObjectCodec, TreeNode}
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}

import java.io.IOException

/**
 * Custom fasterXml Deserializer for CLPublicKey object
 */
class CLPublicKeyDeserializer extends JsonDeserializer[CLPublicKey] {
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): CLPublicKey = {
     CLPublicKey(parser.getText).get
  }
}
