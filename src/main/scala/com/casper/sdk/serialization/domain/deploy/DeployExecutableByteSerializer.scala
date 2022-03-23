package com.casper.sdk.serialization.domain.deploy

import com.casper.sdk.domain.deploy._
import com.casper.sdk.serialization.BytesSerializable
import com.casper.sdk.types.cltypes
import com.casper.sdk.types.cltypes.CLValue
import com.casper.sdk.util.{HexUtils, JsonConverter}

import java.nio.charset.StandardCharsets
import scala.collection.mutable.ArrayBuilder

/**
 * DeployExecutableByteSerializer
 */
class DeployExecutableByteSerializer extends BytesSerializable[DeployExecutable] {

  def toBytes(value: DeployExecutable): Array[Byte] = {

    require(value != null)
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

      case storedContractByHash: StoredContractByHash => {

        if(storedContractByHash.hash.isDefined) builder.addAll(storedContractByHash.hash.get.hash)
        builder.addAll(CLValue.U32(storedContractByHash.entry_point.getBytes(StandardCharsets.UTF_8).length).bytes)
          .addAll(storedContractByHash.entry_point.getBytes(StandardCharsets.UTF_8))
      }
      case storedVersionedContractByHash: StoredVersionedContractByHash => {

        if(storedVersionedContractByHash.hash.isDefined)
        builder.addAll(storedVersionedContractByHash.hash.get.hash)
        storedVersionedContractByHash.version match {
          case None => builder.addOne(0x00.toByte)
          case Some(a) => builder.addOne(0x01.toByte).addAll(CLValue.U32(storedVersionedContractByHash.version.get).bytes)
        }
      }
      case storedVersionedContractByName: StoredVersionedContractByName => {
        builder.addAll(CLValue.String(storedVersionedContractByName.name).bytes)
        storedVersionedContractByName.version match {
          case None => builder.addOne(0x00.toByte)
          case Some(a) => builder.addOne(0x01.toByte).addAll(CLValue.U32(storedVersionedContractByName.version.get).bytes)
        }
      }
      case transfer: DeployTransfer =>
    }
    builder.addAll(argsToBytes(value.args))
    builder.result()
  }

  /**
   * bytes serialization of args field
   *
   * @param list
   * @return
   */
  def argsToBytes(list: Seq[Seq[DeployNamedArg]]): Array[Byte] = {
    require(list != null && !list.isEmpty)
    val builder = new ArrayBuilder.ofByte
    builder.addAll(CLValue.U32(list(0).size).bytes)
    val argSerializer = new DeployNamedArgByteSerializer()
    val seq = list(0)
    for (i <- 0 to seq.size - 1) {
      val subArg = seq(i)
      builder.addAll(argSerializer.toBytes(subArg))
    }
    builder.result()
  }
}
