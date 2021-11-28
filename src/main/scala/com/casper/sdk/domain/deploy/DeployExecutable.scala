package com.casper.sdk.domain.deploy

//abstract
class DeployExecutable (val args: Set[Set[DeployNamedArg]])  extends BytesSerializable {

  override def  encode() : Array[Byte] ={
    null
  }
  //def tag(): Int

}


