package com.casper.sdk.serialization.domain.deploy

import com.casper.sdk.domain.deploy.{Deploy, DeployApproval, DeployExecutable, DeployHeader}
import com.casper.sdk.serialization.BytesSerializable
import com.casper.sdk.types.cltypes.CLValue
import com.casper.sdk.util.HexUtils

import scala.collection.mutable.ArrayBuilder

/**
 * DeployByteSerializer
 */
class DeployByteSerializer extends BytesSerializable[Deploy] {

  def toBytes(value: Deploy): Array[Byte] = {
    require(value != null)
    val builder = new ArrayBuilder.ofByte
    val deployExecutableByteSerializer = new DeployExecutableByteSerializer()
    val approvalByteSerializer = new DeployApprovalByteSerializer()
    val deployHeaderByteSerializer = new DeployHeaderByteSerializer()

    builder.addAll(deployHeaderByteSerializer.toBytes(value.header))
    if (value.hash.isDefined)
      builder.addAll(value.hash.get.hash)
    builder.addAll(deployExecutableByteSerializer.toBytes(value.payment))
      .addAll(deployExecutableByteSerializer.toBytes(value.session))
      .addAll(CLValue.U32(value.approvals.size).bytes)
    for (approuval <- value.approvals) builder.addAll(approvalByteSerializer.toBytes(approuval))
    builder.result()
  }
}
