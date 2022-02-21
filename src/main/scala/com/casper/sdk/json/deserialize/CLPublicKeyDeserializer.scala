package com.casper.sdk.json.deserialize

import com.casper.sdk.types.cltypes.{CLPublicKey, CLValue}
import com.fasterxml.jackson.core.{JsonParser, ObjectCodec, TreeNode}
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}
import scala.util.{Failure, Success, Try}

/**
 * Custom fasterXml Deserializer for CLPublicKey object
 */
class CLPublicKeyDeserializer extends JsonDeserializer[CLPublicKey] {
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): CLPublicKey =Try {
      CLPublicKey(parser.getText).get
    }
    match {
      case Success(x) => x
      case _ => null
    }
}
