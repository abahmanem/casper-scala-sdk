package com.casper.sdk.domain.deploy

import com.casper.sdk.json.deserialize.DeployExecutableDeserializer
import com.casper.sdk.serialization.BytesSerializable
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

/**
 * DeployExecutable
 * @param args
 */
@JsonDeserialize(`using` = classOf[DeployExecutableDeserializer])
abstract class DeployExecutable (val args: Seq[Seq[DeployNamedArg]]) {
  def tag: Int
}


