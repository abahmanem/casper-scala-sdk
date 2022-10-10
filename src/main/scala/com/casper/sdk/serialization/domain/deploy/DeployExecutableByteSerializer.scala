package com.casper.sdk.serialization.domain.deploy

import com.casper.sdk.domain.deploy.*
import com.casper.sdk.serialization.BytesSerializable
import com.casper.sdk.types.cltypes.*
import com.casper.sdk.types.cltypes.{CLTypeInfo, CLValue}
import com.casper.sdk.util.HexUtils

import java.nio.charset.StandardCharsets
import scala.collection.mutable.ArrayBuilder
import scala.util.{Failure, Success, Try}
/**
 * DeployExecutableByteSerializer
 */
class DeployExecutableByteSerializer extends BytesSerializable[DeployExecutable] {

  def toBytes(value: DeployExecutable): Option[Array[Byte]] = Try {
    val builder = new ArrayBuilder.ofByte
    //tag
    builder.addOne(value.tag.toByte)

    value match {
      case modulesbytes: ModuleBytes => builder.addAll(CLValue.getBytes(CLValue.U32(modulesbytes.module_bytes.length))).addAll(modulesbytes.module_bytes)


      case storedContractByName: StoredContractByName => {
        builder.addAll( CLValue.getBytes(CLValue.String(storedContractByName.name)))// .map(v=>v.bytes).getOrElse(Array.emptyByteArray))
        .addAll(CLValue.getBytes(CLValue.String(storedContractByName.entry_point)))//.map(v=>v.bytes).getOrElse(Array.emptyByteArray))
      }

      case storedContractByHash: StoredContractByHash => {

        //if(storedContractByHash.hash.isDefined)
          builder.addAll(storedContractByHash.hash.hash)
          .addAll(CLValue.getBytes( CLValue.U32(storedContractByHash.entry_point.getBytes(StandardCharsets.UTF_8).length)))//.map(v=>v.bytes).getOrElse(Array.emptyByteArray))
          .addAll(storedContractByHash.entry_point.getBytes(StandardCharsets.UTF_8))
      }
      case storedVersionedContractByHash: StoredVersionedContractByHash => {

       // if(storedVersionedContractByHash.hash.isDefined)
        builder.addAll(storedVersionedContractByHash.hash.hash)
          //.addOne(0x01.toByte)

          if(storedVersionedContractByHash.version==0)
            builder  .addAll(CLValue.getBytes( CLValue.OptionNone(CLTypeInfo(CLType.U32))))//.map(v=>v.bytes).getOrElse(Array.emptyByteArray))
          else
            builder  .addAll(CLValue.getBytes(CLValue.Option(CLValue.U32(storedVersionedContractByHash.version))))//.map(v=>v.bytes).getOrElse(Array.emptyByteArray))
              //.U32(storedVersionedContractByHash.version).get.bytes)
       /* storedVersionedContractByHash.version match {
          case None => builder.addOne(0x00.toByte)
          case Some(a) => builder.addOne(0x01.toByte).addAll(CLValue.U32(storedVersionedContractByHash.version).get.bytes)
        }*/
      }
      case storedVersionedContractByName: StoredVersionedContractByName => {
        builder.addAll(CLValue.getBytes( CLValue.String(storedVersionedContractByName.name)))//.map(v=>v.bytes).getOrElse(Array.emptyByteArray))
        //.addOne(0x01.toByte).addAll(CLValue.U32(storedVersionedContractByName.version).map(v=>v.bytes).getOrElse(Array.emptyByteArray))

        if (storedVersionedContractByName.version == 0)
          builder.addAll(CLValue.getBytes(CLValue.OptionNone(CLTypeInfo(CLType.U32))))//.map(v=>v.bytes).getOrElse(Array.emptyByteArray))
        else
          builder.addAll(CLValue.getBytes(CLValue.Option(CLValue.U32(storedVersionedContractByName.version))))//.map(v=>v.bytes).getOrElse(Array.emptyByteArray))
       /* storedVersionedContractByName.version match {
          case None => builder.addOne(0x00.toByte)
          case Some(a) => builder.addOne(0x01.toByte).addAll(CLValue.U32(storedVersionedContractByName.version).get.bytes)
        }*/
      }
      case transfer: Transfer =>
    }
    builder.addAll(argsToBytes(value.args).get)
    builder.result()
  }.toOption

  /**
   * bytes serialization of args field
   *
   * @param list
   * @return
   */
  def argsToBytes(list: Seq[DeployNamedArg]): Option[Array[Byte]] = Try {
    val builder = new ArrayBuilder.ofByte
    builder.addAll( CLValue.getBytes(CLValue.U32(list.size))) //.map(v=>v.bytes).getOrElse(Array.emptyByteArray)   )
    val argSerializer = new DeployNamedArgByteSerializer()
    for (i <- 0 to list.size - 1) {
      val subArg = list(i)
      builder.addAll(argSerializer.toBytes(subArg).getOrElse(Array.emptyByteArray))
    }
    builder.result()
  }.toOption
}
