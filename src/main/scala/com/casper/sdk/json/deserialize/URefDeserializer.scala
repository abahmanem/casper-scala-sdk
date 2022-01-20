package com.casper.sdk.json.deserialize


import com.casper.sdk.types.cltypes.URef
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}

import java.io.IOException

/**
 * Custom fasterXml Deserializer for URef objects
 */
class URefDeserializer extends JsonDeserializer[URef] {
  @throws[IOException]
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): URef = {
    URef(parser.getText)
  }
}
