package com.casper.sdk.json.deserialize

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}
import com.casper.sdk.types.cltypes.AccountHash
import java.io.IOException

/**
 * Custom fasterXml Deserializer for AccountHash objects
 */
class AccountHashDeserializer extends JsonDeserializer[AccountHash] {
  @throws[IOException]
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): AccountHash = {
    new AccountHash(parser.getText)
  }
}
