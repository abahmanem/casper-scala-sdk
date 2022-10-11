package com.casper.sdk.serialization.domain.deploy

import com.casper.sdk.domain.deploy.{Deploy, DeployApproval, DeployExecutable, DeployHeader}
import com.casper.sdk.serialization.BytesSerializable
import com.casper.sdk.types.cltypes.CLValue
import scala.collection.mutable.ArrayBuilder
import scala.util.Try

/**
 * DeployByteSerializer
 */
class DeployByteSerializer extends BytesSerializable[Deploy] {
  def toBytes(value: Deploy): Option[Array[Byte]] = Try {
    val builder = new ArrayBuilder.ofByte
    val deployExecutableByteSerializer = new DeployExecutableByteSerializer()
    val approvalByteSerializer = new DeployApprovalByteSerializer()
    val deployHeaderByteSerializer = new DeployHeaderByteSerializer()
    builder.addAll(deployHeaderByteSerializer.toBytes(value.header).get)
    builder.addAll(value.hash.map(h => h.hash).get)
      .addAll(deployExecutableByteSerializer.toBytes(value.payment).get)
      .addAll(deployExecutableByteSerializer.toBytes(value.session).get)
      .addAll(CLValue.getBytes(CLValue.U32(value.approvals.size)))
    for (approuval <- value.approvals) builder.addAll(approvalByteSerializer.toBytes(approuval).get)
    builder.result()
  }.toOption
}
