package com.casper.sdk.serialization.domain.deploy

import com.casper.sdk.domain.deploy.DeployApproval
import com.casper.sdk.serialization.BytesSerializable

import scala.collection.mutable.ArrayBuilder

/**
 * ByteSerializer for DeployApproval object
 */
class DeployApprovalByteSerializer extends BytesSerializable[DeployApproval] {

  def toBytes(value: DeployApproval): Array[Byte] = {
    assert(value != null)
    val builder = new ArrayBuilder.ofByte
    builder.addAll(value.signer.formatAsByteAccount).addAll(value.signature.formatAsByteAccount).result()
  }
}