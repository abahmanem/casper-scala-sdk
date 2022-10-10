package com.casper.sdk.serialization.cltypes

import com.casper.sdk.serialization.BytesSerializable
import com.casper.sdk.types.cltypes._
import com.casper.sdk.util.{ByteUtils, HexUtils}

import scala.collection.mutable.ArrayBuilder
import scala.util.{Failure, Success, Try}

/**
 * CLValueByteSerializer 
 */

class CLValueByteSerializer extends BytesSerializable[CLValue] {

  def toBytes(value: CLValue): Option[Array[Byte]] =
    Try {
      val builder = new ArrayBuilder.ofByte
      builder.addAll(CLValue.getBytes(CLValue.U32(value.bytes.length)))
      builder.addAll(value.bytes)
      CLTypesToBytes(builder, value.cl_infoType)
      builder.result()
    }.toOption

  /**
   * Serialization for CLType
   *
   * @param builder
   * @param innerType
   * @return Array[Byte]
   */
  def CLTypesToBytes(builder: ArrayBuilder.ofByte, innerType: CLTypeInfo): Unit = {
    //builder.addOne(innerType.cl_Type.clType.toByte)
    innerType match {

      case CLTypeInfo(CLType.Bool) | CLTypeInfo(CLType.I32) | CLTypeInfo(CLType.I64)
           | CLTypeInfo(CLType.U8) | CLTypeInfo(CLType.U32) | CLTypeInfo(CLType.U64)
           | CLTypeInfo(CLType.U128) | CLTypeInfo(CLType.U256) | CLTypeInfo(CLType.U512)
           | CLTypeInfo(CLType.Unit) | CLTypeInfo(CLType.String) | CLTypeInfo(CLType.Key)
           | CLTypeInfo(CLType.URef) | CLTypeInfo(CLType.PublicKey) => builder.addOne(innerType.cl_Type.clType.toByte)

      case option: CLOptionTypeInfo => {
        builder.addOne(innerType.cl_Type.clType.toByte)
        CLTypesToBytes(builder, option.inner.get)
      }
      case result: CLResultTypeInfo => {
        builder.addOne(innerType.cl_Type.clType.toByte)
        CLTypesToBytes(builder, result.okCLinfo)
        CLTypesToBytes(builder, result.errCLinfo)
      }
      case bytearray: CLByteArrayTypeInfo => {
        builder.addOne(innerType.cl_Type.clType.toByte)
        if (CLValue.U32(bytearray.size).isDefined)
          builder.addAll(CLValue.getBytes(CLValue.U32(bytearray.size)))
      }
      case list: CLListTypeInfo => {
        builder.addOne(innerType.cl_Type.clType.toByte)
        CLTypesToBytes(builder, list.cltypeInfo)
      }
      case tuple1: CLTuple1TypeInfo => {
        builder.addOne(innerType.cl_Type.clType.toByte)
        builder.addOne(innerType.cl_Type.clType.toByte)
        CLTypesToBytes(builder, tuple1.typeinfo1)
      }
      case tuple2: CLTuple2TypeInfo => {
        builder.addOne(innerType.cl_Type.clType.toByte)
        CLTypesToBytes(builder, tuple2.typeinfo1)
        CLTypesToBytes(builder, tuple2.typeinfo2)
      }
      case tuple3: CLTuple3TypeInfo => {
        builder.addOne(innerType.cl_Type.clType.toByte)
        CLTypesToBytes(builder, tuple3.typeinfo1)
        CLTypesToBytes(builder, tuple3.typeinfo2)
        CLTypesToBytes(builder, tuple3.typeinfo3)
      }
    }
  }
}