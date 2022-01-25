package com.casper.sdk.json.deserialize

import com.casper.sdk.crypto.hash.Hash
import com.casper.sdk.types.cltypes.CLPublicKey
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}

import java.io.IOException

class HashDeserializer extends JsonDeserializer[Hash] {
  @throws[IOException]
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): Hash = {
    new Hash(parser.getText)
  }
}