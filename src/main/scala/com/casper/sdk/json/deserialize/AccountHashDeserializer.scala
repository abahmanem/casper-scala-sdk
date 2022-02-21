package com.casper.sdk.json.deserialize

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}
import com.casper.sdk.types.cltypes.{AccountHash, CLPublicKey}


import scala.util.{Success, Try}

/**
 * Custom fasterXml Deserializer for AccountHash objects
 */
class AccountHashDeserializer extends JsonDeserializer[AccountHash] {
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): AccountHash = Try {
      AccountHash(parser.getText).get
    }
    match {
      case Success(x) => x
      case _ => null
    }
}




