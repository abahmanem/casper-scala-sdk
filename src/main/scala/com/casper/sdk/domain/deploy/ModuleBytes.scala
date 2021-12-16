package com.casper.sdk.domain.deploy

case class ModuleBytes (
                   val modules_bytes :String,
                    override val args: Seq[Seq[DeployNamedArg]]
                  ) extends DeployExecutable(args) {

  override def  encode() : Array[Byte] ={
    null
  }

}


