package com.casper.sdk.json.deserialize

import com.casper.sdk.crypto.hash.Hash
import com.casper.sdk.types.cltypes.CLPublicKey
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}
import scala.util.{Success, Try}

class HashDeserializer extends JsonDeserializer[Hash] {
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): Hash = Try {
    new Hash(parser.getText)
  }
  match {
    case Success(x) => x
    case _ => null
  }
}