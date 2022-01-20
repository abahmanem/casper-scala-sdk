package com.casper.sdk.domain.deploy

import com.casper.sdk.json.deserialize.DeployExecutableDeserializer
import com.casper.sdk.json.serialize.DeployExecutableSerializer
import com.casper.sdk.serialization.BytesSerializable
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 * DeployExecutable
 * @param args
 */

@JsonSerialize(`using` = classOf[DeployExecutableSerializer])
@JsonDeserialize(`using` = classOf[DeployExecutableDeserializer])
abstract class DeployExecutable (var args: Seq[Seq[DeployNamedArg]]) {
  def tag: Int
}


