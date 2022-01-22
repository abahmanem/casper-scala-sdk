package com.casper.sdk.domain.deploy

/**
 * StoredContractByHash entity object
 * @param hash
 * @param entry_point
 * @param args
 */
case class StoredContractByHash(
                                 hash:Hash,
                                 entry_point:String,
                                 override val args: Seq[Seq[DeployNamedArg]]
                               )  extends DeployExecutable(args) {

  override def tag=1
}
