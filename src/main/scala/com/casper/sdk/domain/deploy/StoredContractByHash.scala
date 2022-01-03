package com.casper.sdk.domain.deploy

case class StoredContractByHash(
                                 hash:String,
                                 entry_point:String,
                                 override  val args: Seq[Seq[DeployNamedArg]]
                               )  extends DeployExecutable(args) {

  def tag=1
}
