package com.casper.sdk.json

import com.casper.sdk.types.cltypes.CLPublicKey
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}
import com.casper.sdk.json.Test01
import scala.util.{Try,Success,Failure}
class TestJsonDeserialzer extends JsonDeserializer[Option[Test01]] {
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): Option[Test01] = Try{
    Test01(Some(parser.getText))
  } match {
    case Success(x) => Some(x)
    case Failure(err) => {
      print("failed due to $err")
      None
    }
  }
}

