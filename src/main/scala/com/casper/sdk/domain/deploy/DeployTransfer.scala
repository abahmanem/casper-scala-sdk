package com.casper.sdk.domain.deploy

/**
 * DeployTransfer Entity
 * @param args
 */
class DeployTransfer (
                     override  val args: Seq[Seq[DeployNamedArg]]
                     )  extends DeployExecutable(args) {
   def tag=5
}
