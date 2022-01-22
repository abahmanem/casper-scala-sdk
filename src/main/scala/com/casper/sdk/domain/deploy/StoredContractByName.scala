package com.casper.sdk.domain.deploy

/**
 * StoredContractByName entity object
 *
 * @param name
 * @param entry_point
 * @param args
 */
case class StoredContractByName(
                                 name: String,
                                 entry_point: String,
                                 override val args: Seq[Seq[DeployNamedArg]]
                               ) extends DeployExecutable(args) {
  override def tag = 2
}
