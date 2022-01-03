package com.casper.sdk.types.cltypes.serialization

import com.casper.sdk.domain.deploy.*
import com.casper.sdk.types.cltypes.*
import com.casper.sdk.types.cltypes.CLValue
import com.casper.sdk.util.{HexUtils, JsonConverter}

import java.nio.charset.StandardCharsets
import scala.collection.mutable.ArrayBuilder

/**
 * DeployExecutableByteSerializer
 */
class DeployExecutableByteSerializer extends BytesSerializable[DeployExecutable] {

  def toBytes(value: DeployExecutable): Array[Byte] = {

    assert(value != null)
    val builder = new ArrayBuilder.ofByte
    //tag
    builder.addOne(value.tag.toByte)

    value match {
      case modulesbytes: ModuleBytes => {
        if (modulesbytes.modules_bytes == null || modulesbytes.modules_bytes.length == 0)
          builder.addAll(CLValue.U32(0).bytes)
        else
          builder.addAll(CLValue.U32(modulesbytes.modules_bytes.length).bytes).addAll(modulesbytes.modules_bytes)
      }

      case storedContractByName: StoredContractByName => {

        builder.addAll(CLValue.String(storedContractByName.name).bytes)
        builder.addAll(CLValue.String(storedContractByName.entry_point).bytes)
      }

      case storedContractByHash: StoredContractByHash =>
        builder.addAll(HexUtils.fromHex(storedContractByHash.hash))
          .addAll(CLValue.U32(storedContractByHash.entry_point.getBytes(StandardCharsets.UTF_8).length).bytes)
          .addAll(storedContractByHash.entry_point.getBytes(StandardCharsets.UTF_8))


      case storedVersionedContractByHash: StoredVersionedContractByHash => {
        builder.addAll(HexUtils.fromHex(storedVersionedContractByHash.hash))
        storedVersionedContractByHash.version match {
          case None => builder.addOne(0x00.toByte)
          case Some(a) => builder.addOne(0x01.toByte).addAll(CLValue.U32(storedVersionedContractByHash.version.get).bytes)
        }
      }

      case transfer: DeployTransfer => {}
    }
    builder.addAll(argsToBytes(value.args))
    builder.result()
  }

  def argsToBytes(list :Seq[Seq[DeployNamedArg]]) : Array[Byte] ={
    val builder = new ArrayBuilder.ofByte
    builder .addAll(CLValue.U32(list.size).bytes)
    val argSerializer = new DeployNamedArgByteSerializer()
    for (i <- 0 to (list.size - 1)) {
      val subArg = list(i)
      builder.addAll(argSerializer.toBytes(subArg(0)))
    }
    builder.result()
  }

}