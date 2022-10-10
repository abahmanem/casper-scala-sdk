package com.casper.sdk.serialization.domain.deploy


import com.casper.sdk.domain.deploy.DeployApproval
import com.casper.sdk.serialization.BytesSerializable
import com.casper.sdk.serialization.cltypes.CLPublicKeyByteSerializer

import scala.collection.mutable.ArrayBuilder
import scala.util.{Failure, Success, Try}

/**
 * ByteSerializer for DeployApproval object
 */
class DeployApprovalByteSerializer extends BytesSerializable[DeployApproval] {
  def toBytes(value: DeployApproval): Option[Array[Byte]] = Try{
    val builder = new ArrayBuilder.ofByte

    builder.addAll(new CLPublicKeyByteSerializer().toBytes(value.signer.get).getOrElse(Array.emptyByteArray))
   // builder.addAll(new CLPublicKeyByteSerializer().toBytes(value.signature.get).getOrElse(Array.emptyByteArray))
    //  builder.addAll(value.signer.map(p =>p.formatAsByteAccount).getOrElse(Array.emptyByteArray))
       .addAll(value.signature.map(s=>s.formatAsByteAccount).getOrElse(Array.emptyByteArray)).result()

  }.toOption
}

