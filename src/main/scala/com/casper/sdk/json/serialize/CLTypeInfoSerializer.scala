package com.casper.sdk.json.serialize

import com.casper.sdk.types.cltypes._
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.{JsonSerializer, SerializerProvider}
import com.casper.sdk.types.cltypes.CLOptionTypeInfo

/**
 * 
 *  CLTypeInfo Custom Json serializer
 */

class CLTypeInfoSerializer extends JsonSerializer[CLTypeInfo] {
  override def serialize(value: CLTypeInfo, gen: JsonGenerator, serializers: SerializerProvider): Unit = {
    assert(value != null)
    serializeCLTypes(value, gen)
  }

  /**
   * serialize CLTypeInfo attribute
   * @param value CLTypeInfo
   * @param gen
   */
  def serializeCLTypes(value: CLTypeInfo, gen: JsonGenerator):Unit={

    value match {
      case option: CLOptionTypeInfo => {
        gen.writeStartObject
        gen.writeFieldName("Option")
        serializeCLTypes(option.inner,gen)
        gen.writeEndObject
      }

      case bytearray: CLByteArrayTypeInfo => {
        gen.writeStartObject
        gen.writeNumberField("ByteArray",bytearray.size)
        gen.writeEndObject
      }
      case clList: CLListTypeInfo => {
        gen.writeStartObject
        gen.writeFieldName("List")
        serializeCLTypes(clList.cltypeInfo,gen)
        gen.writeEndObject
      }

      case cLtuple: CLTuple1TypeInfo => {
        gen.writeStartObject
        gen.writeStartArray("Tuple1")
        serializeCLTypes(cLtuple.typeinfo1,gen)
        gen.writeEndArray()
        gen.writeEndObject
      }

      case cLtuple: CLTuple2TypeInfo => {
        gen.writeStartObject
        gen.writeStartArray("Tuple2")
        serializeCLTypes(cLtuple.typeinfo1,gen)
        serializeCLTypes(cLtuple.typeinfo2,gen)
        gen.writeEndArray()
        gen.writeEndObject
      }

      case cLtuple: CLTuple3TypeInfo => {
        gen.writeStartObject
        gen.writeStartArray("Tuple3")
        serializeCLTypes(cLtuple.typeinfo1,gen)
        serializeCLTypes(cLtuple.typeinfo2,gen)
        serializeCLTypes(cLtuple.typeinfo3,gen)
        gen.writeEndArray()
        gen.writeEndObject
      }

      case cLresult: CLResultTypeInfo => {
        gen.writeStartObject
        gen.writeStartArray("Result")
        gen.writeStartObject
        gen.writeFieldName("ok")
        serializeCLTypes(cLresult.okCLinfo,gen)
        gen.writeFieldName("err")
        serializeCLTypes(cLresult.errCLinfo,gen)
        gen.writeEndObject
        gen.writeEndObject
      }

      case cLresult: CLKeyInfo => {
        gen.writeString("Key")
      }

      // We don't code Map as it will be deprecated in the future.
      case _ => gen.writeString(value.cl_Type.toString)
    }
  }

}