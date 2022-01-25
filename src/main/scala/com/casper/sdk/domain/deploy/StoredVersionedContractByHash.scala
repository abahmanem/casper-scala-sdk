package com.casper.sdk.domain.deploy

import com.casper.sdk.crypto.hash.Hash

/**
 * StoredVersionedContractByHash entity object
 *
 * @param hash
 * @param version
 * @param entry_point
 * @param args
 */
case class StoredVersionedContractByHash(
                                          hash: Option[Hash],
                                          version: Option[Int],
                                          entry_point: String,
                                          override val args: Seq[Seq[DeployNamedArg]]
                                        ) extends DeployExecutable(args) {

  override def tag = 3
}
