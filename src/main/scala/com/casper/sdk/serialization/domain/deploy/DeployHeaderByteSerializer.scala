package com.casper.sdk.serialization.domain.deploy

import com.casper.sdk.domain.deploy.*
import com.casper.sdk.serialization.BytesSerializable
import com.casper.sdk.serialization.cltypes.CLPublicKeyByteSerializer
import com.casper.sdk.types.cltypes.CLValue
import com.casper.sdk.util.{HexUtils, TimeUtil}

import java.nio.charset.StandardCharsets
import scala.collection.mutable.ArrayBuilder
import scala.util.{Failure, Success, Try}
/** *
 * DeployHeaderByteSerializer
 */

class DeployHeaderByteSerializer extends BytesSerializable[DeployHeader] {
  def toBytes(value: DeployHeader): Option[Array[Byte]] = Try{


    val builder = new ArrayBuilder.ofByte
   // if (value.account.isDefined)
     // builder.addOne(value.account.map(p=>p.tag).getOrElse(1).toByte).addAll(value.account.map(p=>p.bytes).getOrElse(Array.emptyByteArray))
    builder.addAll(new CLPublicKeyByteSerializer().toBytes(value.account.get).getOrElse(Array.emptyByteArray))
    //if (value.timestamp.isDefined)
      builder.addAll(  CLValue.getBytes  (CLValue.U64(TimeUtil.ToEpochMs(value.timestamp).getOrElse(0L)))) // CLValue.U64(TimeUtil.ToEpochMs(value.timestamp).getOrElse(0L)).map(v=>v.bytes).getOrElse(Array.emptyByteArray)) )
  //  if (value.ttl.isDefined)
      builder.addAll(  CLValue.getBytes (CLValue.U64(TimeUtil.ttlToMillis(value.ttl).getOrElse(0L))))//  CLValue.U64(TimeUtil.ttlToMillis(value.ttl).getOrElse(0L)).map(v=>v.bytes).getOrElse(Array.emptyByteArray)) )
        .addAll(CLValue.getBytes(CLValue.U64(value.gas_price))) //.map(v=>v.bytes).getOrElse(Array.emptyByteArray))   )
  //  if (value.body_hash.isDefined)
    .addAll(value.body_hash.get.hash)
    builder.addAll( CLValue.getBytes(CLValue.U32(value.dependencies.size)))//  CLValue.U32(value.dependencies.size).map(v=>v.bytes).getOrElse(Array.emptyByteArray)) )
    for (dep <- value.dependencies) builder.addAll(dep.hash)
    builder.addAll( CLValue.getBytes(CLValue.U32(value.chain_name.getBytes(StandardCharsets.UTF_8).length))) //  CLValue.U32(value.chain_name.getBytes(StandardCharsets.UTF_8).length).map(v=>v.bytes).getOrElse(Array.emptyByteArray)) )
    builder.addAll(value.chain_name.getBytes(StandardCharsets.UTF_8))
    builder.result()
  }.toOption
}

