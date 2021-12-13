package com.casper.sdk.json.deserialize

import com.casper.sdk.types.cltypes.{CLPublicKey, CLValue}
import com.fasterxml.jackson.core.{JsonParser, ObjectCodec, TreeNode}
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}

import java.io.IOException

/**
 * Custom Deserializer for CLPublicKey
 */
class CLPublicKeyDeserializer extends JsonDeserializer[CLValue] {
  @throws[IOException]
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): CLPublicKey = {
    new CLPublicKey(parser.getText)
  }
}
