package com.casper.sdk.serialization.domain.deploy

import com.casper.sdk.domain.deploy.DeployNamedArg
import com.casper.sdk.serialization.BytesSerializable
import com.casper.sdk.serialization.cltypes.CLValueByteSerializer
import com.casper.sdk.types.cltypes.CLValue
import com.casper.sdk.util.{ByteUtils, HexUtils}

import java.nio.charset.StandardCharsets
import scala.collection.mutable.ArrayBuilder
import scala.util.{Failure, Success, Try}
/**
 * DeployNamedArg bytes serializer
 */
class DeployNamedArgByteSerializer extends BytesSerializable[DeployNamedArg] {
  def toBytes(value: DeployNamedArg): Option[Array[Byte]] = Try {
    val builder = new ArrayBuilder.ofByte
    builder.addAll( CLValue.getBytes(CLValue.U32(value.name.getBytes().length))) //.map(v=>v.bytes).getOrElse(Array.emptyByteArray))
      .addAll(value.name.getBytes())
      .addAll(new CLValueByteSerializer().toBytes(value.value.getOrElse(CLValue.Unit().get)).getOrElse(Array.emptyByteArray))
    builder.result()
  }.toOption
}