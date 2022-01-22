package com.casper.sdk.domain.deploy

/**
 * StoredVersionedContractByName entity object
 *
 * @param name
 * @param version
 * @param entry_point
 * @param args
 */
case class StoredVersionedContractByName(
                                          name: String,
                                          version: Option[Int],
                                          entry_point: String,
                                          override val args: Seq[Seq[DeployNamedArg]]
                                        ) extends DeployExecutable(args) {
  override def tag = 4
}