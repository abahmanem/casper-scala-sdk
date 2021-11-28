package com.casper.sdk.domain.deploy

class StoredVersionedContractByHash (
                                      hash:String,
                                      version:Option[Int],entry_point:String,
                                      override  val args: Set[Set[DeployNamedArg]]
                                    )  extends DeployExecutable(args) {


  override def  encode() : Array[Byte] ={
    null
  }
}
