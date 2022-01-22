package com.casper.sdk.serialization.domain.deploy

import com.casper.sdk.domain.deploy.DeployNamedArg
import com.casper.sdk.serialization.BytesSerializable
import com.casper.sdk.serialization.cltypes.CLValueByteSerializer
import com.casper.sdk.types.cltypes.CLValue
import com.casper.sdk.util.{ByteUtils, HexUtils}

import java.nio.charset.StandardCharsets
import scala.collection.mutable.ArrayBuilder

/**
 * DeployNamedArg bytes serializer
 */
class DeployNamedArgByteSerializer extends BytesSerializable[DeployNamedArg] {

  def toBytes(value: DeployNamedArg): Array[Byte] = {
    require(value != null)
    val builder = new ArrayBuilder.ofByte
    builder.addAll(CLValue.U32(value.name.getBytes().length).bytes)
    .addAll(value.name.getBytes())
    .addAll(new CLValueByteSerializer().toBytes(value.value))
     builder.result()
  }
}