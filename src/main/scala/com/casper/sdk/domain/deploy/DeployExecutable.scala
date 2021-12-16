package com.casper.sdk.domain.deploy

import com.casper.sdk.json.deserialize.DeployExecutableDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

//abstract
@JsonDeserialize(`using` = classOf[DeployExecutableDeserializer])
abstract class DeployExecutable (val args: Seq[Seq[DeployNamedArg]])  extends BytesSerializable {

  override def  encode() : Array[Byte] ={
    null
  }
  //def tag(): Int

}


