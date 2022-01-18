package com.casper.sdk.domain.deploy

import com.casper.sdk.crypto.hash.Blake2b256
import com.casper.sdk.types.cltypes.CLPublicKey
import com.casper.sdk.domain.deploy.Hash
import com.casper.sdk.serialization.domain.deploy.DeployHeaderByteSerializer
import scala.collection.mutable.ArrayBuilder
/**
 * DeployHeader Entity class
 * @param account
 * @param timestamp
 * @param ttl
 * @param gas_price
 * @param body_hash
 * @param dependencies
 * @param chain_name
 */
case class DeployHeader(
                         account: CLPublicKey,
                         timestamp: String,
                         ttl: String,
                         gas_price: Int,
                         body_hash: Hash,
                         dependencies: Seq[Hash],
                         chain_name: String
                       )
{


  /**
   * compute hedaer hash
   * @return
   */
  def deployHeaderHash:Array[Byte]={
    val serializer = DeployHeaderByteSerializer()
    val builder = new ArrayBuilder.ofByte
    builder.addAll(serializer.toBytes(this))
    Blake2b256.hash(builder.result())
  }
 }

