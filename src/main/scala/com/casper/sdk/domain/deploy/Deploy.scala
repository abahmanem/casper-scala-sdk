package com.casper.sdk.domain.deploy

import com.casper.sdk.crypto.KeyPair
import com.casper.sdk.crypto.hash.{Blake2b256, Hash}
import com.casper.sdk.domain.deploy._
import com.casper.sdk.serialization.domain.deploy.{DeployExecutableByteSerializer, DeployHeaderByteSerializer}
import com.casper.sdk.types.cltypes.{AccountHash, CLPublicKey, Signature}
import com.casper.sdk.util.HexUtils

import scala.collection.mutable.ArrayBuilder
import scala.util.{Failure, Success, Try}

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
                   val hash: Option[Hash],
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
    require(header!=null)
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
    require(header!=null)
    val deployHeader = DeployHeader(header.account, header.timestamp, header.ttl, header.gas_price,
      Some(Hash(deployBodyHash(payment, session))), header.dependencies, header.chain_name)
    new Deploy(Some(Hash(deployHeaderHash(deployHeader))), deployHeader, payment, session, Seq.empty)
  }

  /**
   * compute body hash
   *
   * @return Array[Byte]
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
   * @return Option[Deploy]
   */

  def signDeploy(deploy: Deploy, keyPair: KeyPair): Option[Deploy] = {
    require(keyPair != null)
    Try {
      val signature = keyPair.sign(deploy.hash.get.hash)
      deploy.addApproval(new DeployApproval(keyPair.publicKey, Some(new Signature(signature, keyPair.publicKey.get.keyAlgorithm))))
      deploy
    } match {
      case Success(x) => Some(x)
      case Failure(err) => None
    }
  }


  /**
   * create a deploy transfert between two accounts
   *
   * @param from   source of the transfer
   * @param to     recipient account
   * @param amount amount to transfer
   * @param fees   payment
   * @param chaine casper chaine name
   * @param id     transfert id
   * @param gas    gas fees
   * @param ttl    deploy time to live
   * @return a  Unsigne dDeploy
   */
  def transfer(from: CLPublicKey, to: CLPublicKey, amount: Long, fees: BigInt, chaine: String, id: BigInt, gas: Int = 1, ttl: Long = 1800000): Deploy = {
    val header = new DeployHeader(Some(from), Option(System.currentTimeMillis()), Option(ttl), gas, None, Seq.empty, chaine)
    createUnsignedDeploy(header, ModuleBytes(fees), DeployTransfer(amount, new AccountHash(to.bytes), id))
  }


  /**
   * create a Deploy to deploy a  smart contract to casper network
   *
   * @param wasm   compiled smart contract bytes
   * @param from   source that will dpeloys the smart contract
   * @param fees   payment
   * @param chaine casper chaine name
   * @param id     id
   * @param gas    gas price
   * @param ttl    TTL
   * @return an  Unsigned Deploy
   */
  def contract(wasm: Array[Byte], from: CLPublicKey, fees: BigInt, chaine: String, gas: Int = 1, ttl: Long = 1800000): Deploy = {
    val header = new DeployHeader(Some(from), Option(System.currentTimeMillis()), Option(ttl), gas, None, Seq.empty, chaine)
    createUnsignedDeploy(header, ModuleBytes(fees), new ModuleBytes(wasm, Seq.empty))
  }

  /**
   * Deploy to call a smart contract on the network (ex: delegation, undelegation)
   *
   * @param name       name of the smart contract
   * @param entryPoint enty point (function) to call in the smart contract
   * @param args       sequence of execution arguments
   * @param from       source of the call on the smart contract
   * @param fees       paymenet amount
   * @param chaine     casper chaine name
   * @param gas        gas price
   * @param ttl        TTL
   * @return an  Unsigned Deploy
   */

  def contractByNameCall(name: String, entryPoint: String, args: Seq[DeployNamedArg], from: CLPublicKey, fees: BigInt, chaine: String, gas: Int = 1, ttl: Long = 1800000): Deploy = {
    val header = new DeployHeader(Some(from), Option(System.currentTimeMillis()), Option(ttl), gas, None, Seq.empty, chaine)
    createUnsignedDeploy(header, ModuleBytes(fees), new StoredContractByName(name, entryPoint, Seq(args)))
  }

  /**
   * Creates a Deploy to call a smart contract on the network
   *
   * @param hash       hash of the smart contract to call
   * @param entryPoint enty point (function) to call in the smart contract
   * @param args       sequence of execution arguments
   * @param from       source of the call on the smart contract
   * @param fees       paymenet amount
   * @param chaine     casper chaine name
   * @param gas        gas price
   * @param ttl        TTL
   * @return an  Unsigned Deploy
   */

  def contractByHashCall(hash: Option[Hash], entryPoint: String, args: Seq[DeployNamedArg], from: CLPublicKey, fees: BigInt, chaine: String, gas: Int = 1, ttl: Long = 1800000): Deploy = {
    val header = new DeployHeader(Some(from), Option(System.currentTimeMillis()), Option(ttl), gas, None, Seq.empty, chaine)
    createUnsignedDeploy(header, ModuleBytes(fees), new StoredContractByHash(hash, entryPoint, Seq(args)))
  }


  /**
   * Creates a Deploy to call a function in a versionned smart contract on the network
   *
   * @param name       name of the smart contract to call
   * @param entryPoint enty point (function) to call in the  smart contract
   * @param version    version of the  contract
   * @param args       sequence of execution arguments
   * @param from       source of the call on the smart contract
   * @param fees       paymenet amount
   * @param chaine     casper chaine name
   * @param gas        gas price
   * @param ttl        TTL
   * @return an  Unsigned Deploy
   */
  def versionnedContractByNameCall(name: String, entryPoint: String, version: Int, args: Seq[DeployNamedArg], from: CLPublicKey, fees: BigInt, chaine: String, gas: Int = 1, ttl: Long = 1800000): Deploy = {
    val header = new DeployHeader(Some(from), Option(System.currentTimeMillis()), Option(ttl), gas, None, Seq.empty, chaine)
    createUnsignedDeploy(header, ModuleBytes(fees), new StoredVersionedContractByName(name, Some(version), entryPoint, Seq(args)))
  }


  /**
   * Creates a Deploy to call a function in a versionned smart contract on the network
   *
   * @param hash       hash of the smart contract to call
   * @param entryPoint enty point (function) to call in the smart contract
   * @param version    version of the  contract
   * @param args       sequence of execution arguments
   * @param from       source of the call on the smart contract
   * @param fees       paymenet amount
   * @param chaine     casper chaine name
   * @param gas        gas price
   * @param ttl        TTL
   * @return an  Unsigned Deploy
   */
  def versionnedContractByHashCall(hash: Option[Hash], entryPoint: String, version: Int, args: Seq[DeployNamedArg], from: CLPublicKey, fees: BigInt, chaine: String, gas: Int = 1, ttl: Long = 1800000): Deploy = {
    val header = new DeployHeader(Some(from), Option(System.currentTimeMillis()), Option(ttl), gas, None, Seq.empty, chaine)
    createUnsignedDeploy(header, ModuleBytes(fees), new StoredVersionedContractByHash(hash, Some(version), entryPoint, Seq(args)))
  }
}

