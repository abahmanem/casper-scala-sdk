package com.casper.sdk.serialization.domain.deploy

import com.casper.sdk.domain.deploy.DeployApproval
import com.casper.sdk.serialization.BytesSerializable

import scala.collection.mutable.ArrayBuilder

/**
 * ByteSerializer for DeployApproval object
 */
class DeployApprovalByteSerializer extends BytesSerializable[DeployApproval] {

  def toBytes(value: DeployApproval): Array[Byte] = {
    require(value != null)
    val builder = new ArrayBuilder.ofByte
    //signer and signature are both set or not
    if (value.signature.isDefined && value.signer.isDefined)
      builder.addAll(value.signer.get.formatAsByteAccount).addAll(value.signature.get.formatAsByteAccount).result()
    else
      builder.result()
  }
}