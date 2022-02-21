package com.casper.sdk.domain.deploy

import com.casper.sdk.crypto.hash.{Blake2b256, Hash}
import com.casper.sdk.types.cltypes.CLPublicKey
import com.casper.sdk.json.deserialize.TimeStampDeSerializer
import com.casper.sdk.json.deserialize.TTLDeserializer
import com.casper.sdk.json.serialize.TimeStampSerializer
import com.casper.sdk.json.serialize.TTLSerializer
import com.casper.sdk.serialization.domain.deploy.DeployHeaderByteSerializer
import com.casper.sdk.util.HexUtils
import com.fasterxml.jackson.databind.annotation.{JsonDeserialize, JsonSerialize}

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
                         account: Option[CLPublicKey],
                         @JsonSerialize(converter = classOf[TimeStampSerializer])
                         @JsonDeserialize(converter = classOf[TimeStampDeSerializer])
                         timestamp: Option[Long],
                         @JsonSerialize(converter =  classOf[TTLSerializer])
                         @JsonDeserialize(converter =  classOf[TTLDeserializer])
                         ttl: Option[Long],
                         gas_price: Int,
                         body_hash: Option[Hash]=None,
                         var dependencies: Seq[Hash],
                         chain_name: String
                       )

