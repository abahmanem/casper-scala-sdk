package com.casper.sdk.serialization.domain.deploy


import com.casper.sdk.domain.deploy.DeployApproval
import com.casper.sdk.serialization.BytesSerializable
import com.casper.sdk.serialization.cltypes.CLPublicKeyByteSerializer

import scala.collection.mutable.ArrayBuilder
import scala.util.Try

/**
 * ByteSerializer for DeployApproval object
 */
class DeployApprovalByteSerializer extends BytesSerializable[DeployApproval] {
  def toBytes(value: DeployApproval): Option[Array[Byte]] = Try {
    val builder = new ArrayBuilder.ofByte
    builder.addAll(new CLPublicKeyByteSerializer().toBytes(value.signer.get).get)
      .addAll(value.signature.map(s => s.formatAsByteAccount).get).result()
  }.toOption
}

