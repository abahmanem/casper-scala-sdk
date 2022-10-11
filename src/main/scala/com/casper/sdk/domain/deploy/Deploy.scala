package com.casper.sdk.domain.deploy

import com.casper.sdk.crypto.KeyPair
import com.casper.sdk.crypto.hash.{Blake2b256, Hash}
import com.casper.sdk.domain.deploy.DeployExecutable
import com.casper.sdk.serialization.domain.deploy.{DeployExecutableByteSerializer, DeployHeaderByteSerializer}
import com.casper.sdk.types.cltypes.{AccountHash, CLPublicKey, Signature}
import com.casper.sdk.util.{HexUtils, TimeUtil}

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
                   hash: Option[Hash],
                   header: DeployHeader,
                   payment: DeployExecutable,
                   session: DeployExecutable,
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

  import io.circe.syntax._
  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

  implicit val decoder: Decoder[Deploy] = deriveDecoder[Deploy]
  implicit val encoder: Encoder[Deploy] = deriveEncoder[Deploy]

  /**
   * compute header hash
   *
   * @return header hash
   */
  def deployHeaderHash(header: DeployHeader): Option[Array[Byte]] = //{
  //val serializer = new DeployHeaderByteSerializer()
    new DeployHeaderByteSerializer().toBytes(header).flatMap(arr => Blake2b256.hash(arr))


  /**
   * Create an unsigned deploy
   *
   * @param header  deploy header
   * @param payment payment DeployExecutable
   * @param session session DeployExecutable
   * @return unsigned Deploy
   */
  def createUnsignedDeploy(header: DeployHeader, payment: DeployExecutable, session: DeployExecutable): Option[Deploy] = Try {

    val deployHeader = DeployHeader(header.account, header.timestamp, header.ttl, header.gas_price,
      Option(new Hash(deployBodyHash(payment, session).get)), header.dependencies, header.chain_name)
    new Deploy(Option(Hash(deployHeaderHash(deployHeader).get)), deployHeader, payment, session, Seq.empty)
  }.toOption

  /**
   * compute body hash
   *
   * @return Array[Byte]
   */
  def deployBodyHash(payment: DeployExecutable, session: DeployExecutable): Option[Array[Byte]] = //Try {
    val serializer = DeployExecutableByteSerializer()
    val builder = new ArrayBuilder.ofByte
    //Try(builder.addAll(serializer.toBytes(payment).get).addAll(serializer.toBytes(session).get)).toOption.get
    Blake2b256.hash(Try(builder.addAll(serializer.toBytes(payment).get).addAll(serializer.toBytes(session).get)).toOption.get.result())
  // }.toOption

  /**
   * Sign a Deploy
   *
   * @param deploy  deploy to sign
   * @param keyPair keyPair to sign deploy with
   * @return Option[Deploy]
   */

  def signDeploy(deploy: Deploy, keyPair: KeyPair): Option[Deploy] = Try {
    val signature = keyPair.sign(deploy.hash.map(h => h.hash).get).toOption
    deploy.addApproval(new DeployApproval(Option(keyPair.publicKey), Option(new Signature(signature.get, keyPair.publicKey.keyAlgorithm))))
    deploy
  }.toOption


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
  def transfer(from: Option[CLPublicKey], to: Option[CLPublicKey], amount: Long, fees: BigInt, chaine: String, id: BigInt, gas: Int = 1, ttl: String = "1800000"): Option[Deploy] = {
    val header = new DeployHeader(from, TimeUtil.timeStampString(System.currentTimeMillis()).get, ttl, gas, None, Seq.empty, chaine)
    createUnsignedDeploy(header, ModuleBytes(fees), Transfer(amount, new AccountHash(to.get.bytes), id))
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
  def contract(wasm: Array[Byte], from: Option[CLPublicKey], fees: BigInt, chain: String, gas: Int = 1, ttl: String = "1800000"): Option[Deploy] = {
    val header = new DeployHeader(from, TimeUtil.timeStampString(System.currentTimeMillis()).get, ttl, gas, None, Seq.empty, chain)
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

  def contractByNameCall(name: String, entryPoint: String, args: Seq[DeployNamedArg], from: Option[CLPublicKey], fees: BigInt, chaine: String, gas: Int = 1,
                         ttl: String = "1800000"): Option[Deploy] = {
    val header = new DeployHeader(from, TimeUtil.timeStampString(System.currentTimeMillis()).get, ttl, gas, None, Seq.empty, chaine)
    createUnsignedDeploy(header, ModuleBytes(fees), new StoredContractByName(name, entryPoint, args))
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

  def contractByHashCall(hash: Hash, entryPoint: String, args: Seq[DeployNamedArg], from: Option[CLPublicKey], fees: BigInt, chaine: String, gas: Int = 1,
                         ttl: String = "1800000"): Option[Deploy] = {
    val header = new DeployHeader(from, TimeUtil.timeStampString(System.currentTimeMillis()).get, ttl, gas, None, Seq.empty, chaine)
    createUnsignedDeploy(header, ModuleBytes(fees), new StoredContractByHash(hash, entryPoint, args))
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
  def versionnedContractByNameCall(name: String, entryPoint: String, version: Int, args: Seq[DeployNamedArg], from: Option[CLPublicKey], fees: BigInt, chaine: String,
                                   gas: Int = 1, ttl: String = "1800000"): Option[Deploy] = {
    val header = new DeployHeader(from, TimeUtil.timeStampString(System.currentTimeMillis()).get, ttl, gas, None, Seq.empty, chaine)
    createUnsignedDeploy(header, ModuleBytes(fees), new StoredVersionedContractByName(name, version, entryPoint, args))
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
  def versionnedContractByHashCall(hash: Hash, entryPoint: String, version: Int, args: Seq[DeployNamedArg], from: Option[CLPublicKey], fees: BigInt, chaine:
  String, gas: Int = 1, ttl: String = "1800000"): Option[Deploy] = {
    val header = new DeployHeader(from, TimeUtil.timeStampString(System.currentTimeMillis()).get, ttl, gas, None, Seq.empty, chaine)
    createUnsignedDeploy(header, ModuleBytes(fees), new StoredVersionedContractByHash(hash, version, entryPoint, args))
  }
}

