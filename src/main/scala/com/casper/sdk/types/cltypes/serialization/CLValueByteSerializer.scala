package com.casper.sdk.types.cltypes.serialization

import com.casper.sdk.types.cltypes.*
import com.casper.sdk.types.cltypes.CLResultTypeInfo
import com.casper.sdk.util.{ByteUtils, HexUtils}

import scala.collection.mutable.ArrayBuilder


/**
 * CLValueByteSerializer 
 */

class CLValueByteSerializer extends BytesSerializable[CLValue] {

  def toBytes(value: CLValue): Array[Byte] = {
    assert(value != null)
    val builder = new ArrayBuilder.ofByte
    builder.addAll(CLValue.U32(value.bytes.length).bytes)
      .addAll(value.bytes)
    CLTypesToBytes(builder, value.cl_infoType)
    builder.result()
  }

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


      case option: CLOptionTypeInfo => CLTypesToBytes(builder, option.inner)
      case result: CLResultTypeInfo => {
        CLTypesToBytes(builder, result.ok)
        CLTypesToBytes(builder, result.err)
      }
      case bytearray: CLByteArrayTypeInfo => builder.addAll(CLValue.U32(bytearray.size).bytes)
      case list: CLListTypeInfo => CLTypesToBytes(builder, list.cltypeInfo)
      case tuple1: CLTuple1TypeInfo => CLTypesToBytes(builder, tuple1.typeinfo1)
      case tuple2: CLTuple2TypeInfo => {
        CLTypesToBytes(builder, tuple2.typeinfo1)
        CLTypesToBytes(builder, tuple2.typeinfo2)
      }
      case tuple3: CLTuple3TypeInfo => {
        CLTypesToBytes(builder, tuple3.typeinfo1)
        CLTypesToBytes(builder, tuple3.typeinfo2)
        CLTypesToBytes(builder, tuple3.typeinfo3)
      }
      //Map
      case _ => throw IllegalArgumentException("unknow type")
    }
  }
}