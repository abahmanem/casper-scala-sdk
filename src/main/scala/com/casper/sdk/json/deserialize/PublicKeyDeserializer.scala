package com.casper.sdk.json.serialize

import com.casper.sdk.types.cltypes.CLValue
import com.fasterxml.jackson.core.{JsonParser, ObjectCodec, TreeNode}
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}
import com.casper.sdk.types.cltypes.CLPublicKey
import java.io.IOException

class PublicKeyDeserializer extends JsonDeserializer[CLValue] {
  @throws[IOException]
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): CLPublicKey = {

    new CLPublicKey(parser.getText)
  }
}
