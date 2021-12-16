package com.casper.sdk.domain.deploy

class StoredVersionedContractByName (
                                     name:String,
                                     version:Option[Int],
                                     entry_point:String,
                                     override  val args: Seq[Seq[DeployNamedArg]]
                                    )  extends DeployExecutable(args) {


  override def  encode() : Array[Byte] ={
    null
  }
}