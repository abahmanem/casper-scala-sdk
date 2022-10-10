package com.casper.sdk.serialization.domain.deploy

import com.casper.sdk.domain.deploy.{Deploy, DeployApproval, DeployExecutable, DeployHeader}
import com.casper.sdk.serialization.BytesSerializable
import com.casper.sdk.types.cltypes.CLValue
import com.casper.sdk.util.HexUtils

import scala.collection.mutable.ArrayBuilder
import scala.util.Try

/**
 * DeployByteSerializer
 */
class DeployByteSerializer extends BytesSerializable[Deploy] {
  def toBytes(value: Deploy): Option[Array[Byte]] =Try{
      val builder = new ArrayBuilder.ofByte
      val deployExecutableByteSerializer = new DeployExecutableByteSerializer()
      val approvalByteSerializer = new DeployApprovalByteSerializer()
      val deployHeaderByteSerializer = new DeployHeaderByteSerializer()
      builder.addAll(deployHeaderByteSerializer.toBytes(value.header).getOrElse(Array.emptyByteArray))
      builder.addAll(value.hash.map(h=>h.hash).getOrElse(Array.emptyByteArray))
        .addAll(deployExecutableByteSerializer.toBytes(value.payment).getOrElse(Array.emptyByteArray))
        .addAll(deployExecutableByteSerializer.toBytes(value.session).getOrElse(Array.emptyByteArray))
        .addAll( CLValue.getBytes( CLValue.U32(value.approvals.size)))
      for (approuval <- value.approvals) builder.addAll(approvalByteSerializer.toBytes(approuval).getOrElse(Array.emptyByteArray))
      builder.result()
    }.toOption
}
