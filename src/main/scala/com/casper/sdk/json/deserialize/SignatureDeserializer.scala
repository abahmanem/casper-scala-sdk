package com.casper.sdk.json.deserialize

import com.casper.sdk.types.cltypes.Signature
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}
import scala.util.{Failure, Success, Try}
/**
 * Custom fasterXml Deserializer for Signature objects
 *
 */

class SignatureDeserializer extends JsonDeserializer[Signature] {
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): Signature = Try{
     Signature(parser.getText).get
  } match {
    case Success(x) => x
    case _ => null
  }
}

