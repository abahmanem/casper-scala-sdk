package com.casper.sdk.domain.deploy

class ModuleBytes (
                    modules_bytes :String,
                    override val args: Set[Set[DeployNamedArg]]
                  ) extends DeployExecutable(args) {

  override def  encode() : Array[Byte] ={
    null
  }

}


