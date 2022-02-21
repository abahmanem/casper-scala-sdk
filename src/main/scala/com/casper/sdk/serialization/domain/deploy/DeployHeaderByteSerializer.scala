package com.casper.sdk.serialization.domain.deploy

import com.casper.sdk.domain.deploy.*
import com.casper.sdk.serialization.BytesSerializable
import com.casper.sdk.types.cltypes.CLValue
import com.casper.sdk.util.HexUtils

import java.nio.charset.StandardCharsets
import scala.collection.mutable.ArrayBuilder

/** *
 * DeployHeaderByteSerializer
 */

class DeployHeaderByteSerializer extends BytesSerializable[DeployHeader] {

  def toBytes(value: DeployHeader): Array[Byte] = {
    require(value != null)
    val builder = new ArrayBuilder.ofByte
    if (value.account.isDefined)
      builder.addOne(value.account.get.tag.toByte).addAll(value.account.get.bytes)
    if (value.timestamp.isDefined)
      builder.addAll(CLValue.U64(BigInt(value.timestamp.get)).bytes)
    if (value.ttl.isDefined)
      builder.addAll(CLValue.U64(BigInt(value.ttl.get)).bytes)
        .addAll(CLValue.U64(value.gas_price).bytes)
    if (value.body_hash.isDefined) builder.addAll(value.body_hash.get.hash)
    builder.addAll(CLValue.U32(value.dependencies.size).bytes)
    for (dep <- value.dependencies) builder.addAll(dep.hash)
    builder.addAll(CLValue.U32(value.chain_name.getBytes(StandardCharsets.UTF_8).length).bytes)
    builder.addAll(value.chain_name.getBytes(StandardCharsets.UTF_8))
    builder.result()
  }
}

