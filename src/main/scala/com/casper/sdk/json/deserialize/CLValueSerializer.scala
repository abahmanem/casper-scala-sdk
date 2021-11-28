package com.casper.sdk.json.deserialize

import com.casper.sdk.types.cltypes.{CLOptionTypeInfo, _}
import com.fasterxml.jackson.core.{JsonParser, ObjectCodec, TreeNode}
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}
import com.fasterxml.jackson.databind.node.{NumericNode, TextNode}

import java.io.IOException


class CLValueSerializer extends JsonDeserializer[CLValue] {


  @throws[IOException]
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): CLValue = {
    null
  }
}