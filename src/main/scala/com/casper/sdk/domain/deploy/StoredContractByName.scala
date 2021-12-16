package com.casper.sdk.domain.deploy

case class StoredContractByName(
                                 name:String,
                                 entry_point:String,
                                 override val args: Seq[Seq[DeployNamedArg]]
                               ) extends DeployExecutable(args) {

  override def  encode() : Array[Byte] ={
    null
  }

}
