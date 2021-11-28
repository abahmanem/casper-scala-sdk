package com.casper.sdk.domain.deploy

case class StoredContractByName(
                                 name:String,
                                 entry_point:String,
                                 override val args: Set[Set[DeployNamedArg]]
                               ) extends DeployExecutable(args) {

  override def  encode() : Array[Byte] ={
    null
  }

}
