package com.casper.sdk.domain.deploy

import com.casper.sdk.crypto.hash.Hash

/**
 * StoredContractByHash entity object
 *
 * @param hash
 * @param entry_point
 * @param args
 */
case class StoredContractByHash(
                                 hash:Option[Hash],
                                 entry_point:String,
                                 override val args: Seq[Seq[DeployNamedArg]]
                               )  extends DeployExecutable(args) {

  override def tag=1
}
