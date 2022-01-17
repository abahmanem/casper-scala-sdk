package com.casper.sdk.domain.deploy

import com.casper.sdk.crypto.KeyPair
import com.casper.sdk.types.cltypes.Signature
import com.casper.sdk.util.HexUtils

/**
 * Deploy entity class
 *
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
                  ) {


  /**
   * add approuval
   * @param approval
   * @return
   */
  def addApproval(approval: DeployApproval)={
    approvals:+approval
  }

  /**
   * sign deploy
   * @param keyPair
   * @return
   */
  def sign(keyPair: KeyPair)={
    require(keyPair!=null)
  val signature = keyPair.sign(HexUtils.fromHex(hash))
  val approval = new DeployApproval(keyPair.cLPublicKey, new Signature(signature,keyPair.cLPublicKey.keyAlgorithm))
  addApproval(approval)

}

}


