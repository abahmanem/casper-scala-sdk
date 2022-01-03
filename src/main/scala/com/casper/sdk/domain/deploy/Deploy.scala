package com.casper.sdk.domain.deploy

/**
 * Deploy entity class
 * @param hash
 * @param header
 * @param payment
 * @param session
 * @param approvals
 */

case class Deploy (
                   val hash:String,
                   val header :DeployHeader,
                   val payment:DeployExecutable,
                   val session:DeployExecutable,
                   val approvals:Seq[DeployApproval]
                  )


