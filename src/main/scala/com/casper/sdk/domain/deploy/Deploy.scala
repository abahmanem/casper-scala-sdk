package com.casper.sdk.domain.deploy

import com.casper.sdk.crypto.KeyPair
import com.casper.sdk.crypto.hash.Blake2b256
import com.casper.sdk.domain.deploy.DeployHeader
import com.casper.sdk.serialization.domain.deploy.{DeployExecutableByteSerializer, DeployHeaderByteSerializer}
import com.casper.sdk.types.cltypes.{CLPublicKey, Signature}
import com.casper.sdk.util.HexUtils

import scala.collection.mutable.ArrayBuilder

/**
 * Deploy entity class
 *
 * @param hash
 * @param header
 * @param payment
 * @param session
 * @param approvals
 */

case class Deploy(
                   val hash: Hash,
                   val header: DeployHeader,
                   val payment: DeployExecutable,
                   val session: DeployExecutable,
                   var approvals: Seq[DeployApproval]
                 ) {

  /**
   * add approuval
   *
   * @param approval
   * @return
   */
  def addApproval(approval: DeployApproval) = {
    val appr = approvals :+ approval
    this.approvals = appr
  }
}


/**
 * Deploy companion object
 */
object Deploy {

  /**
   * compute header hash
   *
   * @return header hash
   */
  def deployHeaderHash(header: DeployHeader): Array[Byte] = {
    val serializer = new DeployHeaderByteSerializer()
    Blake2b256.hash(serializer.toBytes(header))
  }


  /**
   * Create an unsigned deploy
   *
   * @param header  deploy header
   * @param payment payment DeployExecutable
   * @param session session DeployExecutable
   * @return unsigned Deploy
   */
  def createUnsignedDeploy(header: DeployHeader, payment: DeployExecutable, session: DeployExecutable): Deploy = {
    val hHash = deployHeaderHash(header)
    val bHash = deployBodyHash(payment, session)
    val deployHeader = DeployHeader(header.account, header.timestamp, header.ttl, header.gas_price,
      new Hash(bHash), header.dependencies, header.chain_name)
    new Deploy(Hash(deployHeaderHash(deployHeader)), deployHeader, payment, session, Seq.empty)
  }

  /**
   * compute body hash
   *
   * @return
   */
  def deployBodyHash(payment: DeployExecutable, session: DeployExecutable): Array[Byte] = {
    require(payment != null && session != null)
    val serializer = DeployExecutableByteSerializer()
    val builder = new ArrayBuilder.ofByte
    builder.addAll(serializer.toBytes(payment)).addAll(serializer.toBytes(session))
    Blake2b256.hash(builder.result())

  }

  /**
   * Sign a Deploy
   *
   * @param deploy  deploy to sign
   * @param keyPair keyPair to sign deploy with
   * @return Deploy
   */

  def signDeploy(deploy: Deploy, keyPair: KeyPair): Deploy = {
    require(keyPair != null)
    val signature = keyPair.sign(deploy.hash.hash)
    deploy.addApproval(new DeployApproval(keyPair.cLPublicKey, new Signature(signature, keyPair.cLPublicKey.keyAlgorithm)))
    deploy
  }
}

