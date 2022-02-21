package com.casper.sdk.json.deserialize

import com.casper.sdk.types.cltypes.URef
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}
import scala.util.{Success, Try}

/**
 * Custom fasterXml Deserializer for URef objects
 */
class URefDeserializer extends JsonDeserializer[URef] {
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): URef = Try{
    URef(parser.getText)
  }
  match {
    case Success(x) => x.get
    case _ => null
  }
}
