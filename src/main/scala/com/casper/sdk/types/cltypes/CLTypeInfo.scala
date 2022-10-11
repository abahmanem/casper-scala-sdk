package com.casper.sdk.types.cltypes


/**
 * CLType info holder
 *
 * @param cl_Type
 */

case class CLTypeInfo(
                       cl_Type: CLType
                     )

object CLTypeInfo {

  import io.circe.{Decoder, Encoder, HCursor, Json}

  implicit val encoder: Encoder[CLTypeInfo] = (value: CLTypeInfo) => serializeCLTypes(value)

  def serializeCLTypes(value: CLTypeInfo): Json = {
    value match {
      case option: CLOptionTypeInfo => Json.obj(("Option", serializeCLTypes(option.inner.get)))
      case bytearray: CLByteArrayTypeInfo => Json.obj(("ByteArray", Json.fromInt(bytearray.size)))
      case clList: CLListTypeInfo => Json.obj(("List", serializeCLTypes(clList.cltypeInfo)))
      case cLtuple1: CLTuple1TypeInfo => Json.obj(("Tuple1", Json.arr(serializeCLTypes(cLtuple1.typeinfo1))))
      case cLtuple2: CLTuple2TypeInfo => Json.obj(("Tuple2", Json.arr(serializeCLTypes(cLtuple2.typeinfo1), serializeCLTypes(cLtuple2.typeinfo2))))
      case cLtuple3: CLTuple3TypeInfo => Json.obj(("Tuple3", Json.arr(serializeCLTypes(cLtuple3.typeinfo1), serializeCLTypes(cLtuple3.typeinfo2), serializeCLTypes(cLtuple3.typeinfo3))))
      case cLresult: CLResultTypeInfo => Json.obj(("Result", Json.obj(("ok", serializeCLTypes(cLresult.okCLinfo)), ("err", serializeCLTypes(cLresult.errCLinfo)))))
      case _ => Json.fromString(value.cl_Type.toString)
    }
  }
}