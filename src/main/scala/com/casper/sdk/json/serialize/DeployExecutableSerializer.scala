package com.casper.sdk.json.serialize

import com.casper.sdk.domain.deploy._
import com.casper.sdk.util.HexUtils
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.{JsonSerializer, SerializerProvider}

/**
 * DeployExecutable Custom Json serializer
 */
class DeployExecutableSerializer extends JsonSerializer[DeployExecutable] {
  override def serialize(value: DeployExecutable, gen: JsonGenerator, serializers: SerializerProvider): Unit = {
    require(value != null)
    value match {
      case modulesbytes: ModuleBytes => writeModuleBytes(modulesbytes, gen)
      case storedContractByName: StoredContractByName => writeStoredContractByName(storedContractByName, gen)
      case storedContractByHash: StoredContractByHash => writeStoredContractByHash(storedContractByHash, gen)
      case storedVersionedContractByHash: StoredVersionedContractByHash => writeStoredVersionedContractByHash(storedVersionedContractByHash, gen)
      case storedVersionedContractByName: StoredVersionedContractByName => writeStoredVersionedContractByName(storedVersionedContractByName, gen)
      case transfer: DeployTransfer => writeDeployTransfer(transfer, gen)
     }

    /**
     * serialize  DeployTransfer
     *
     * @param modulesbytes
     * @param gen
     */
    def writeDeployTransfer(deployTransfer: DeployTransfer, gen: JsonGenerator) = {
      gen.writeStartObject
      gen.writeFieldName("Transfer")
      gen.writeStartObject
      serializeArgs(deployTransfer, gen)
      gen.writeEndObject
      gen.writeEndObject
    }

    /**
     * serialize  ModuleBytes
     *
     * @param modulesbytes
     * @param gen
     */
    def writeModuleBytes(modulesbytes: ModuleBytes, gen: JsonGenerator) = {
      gen.writeStartObject
      gen.writeFieldName("ModuleBytes")
      gen.writeStartObject
      gen.writeFieldName("module_bytes")
      gen.writeString(HexUtils.toHex(modulesbytes.modules_bytes).get)
      serializeArgs(modulesbytes, gen)
      gen.writeEndObject
      gen.writeEndObject
    }

    /**
     * serialize  storedContractByName
     *
     * @param modulesbytes
     * @param gen
     */
    def writeStoredContractByName(storedContractByName: StoredContractByName, gen: JsonGenerator) = {
      gen.writeStartObject
      gen.writeFieldName("StoredContractByName")
      gen.writeStartObject
      gen.writeFieldName("name")
      gen.writeString(storedContractByName.name)
      gen.writeFieldName("entry_point")
      gen.writeString(storedContractByName.entry_point)
      serializeArgs(storedContractByName, gen)
      gen.writeEndObject
      gen.writeEndObject
    }


    /**
     * serialize  storedContractByHash
     *
     * @param modulesbytes
     * @param gen
     */
    def writeStoredContractByHash(storedContractByHash: StoredContractByHash, gen: JsonGenerator) = {
      gen.writeStartObject
      gen.writeFieldName("StoredContractByHash")
      gen.writeStartObject
      gen.writeFieldName("hash")
      gen.writeString(HexUtils.toHex(storedContractByHash.hash.get.hash).get)
      gen.writeFieldName("entry_point")
      gen.writeString(storedContractByHash.entry_point)
      serializeArgs(storedContractByHash, gen)
      gen.writeEndObject
      gen.writeEndObject
    }

    /**
     * serialize  storedVersionedContractByHash
     *
     * @param modulesbytes
     * @param gen
     */

    def writeStoredVersionedContractByHash(storedVersionedContractByHash: StoredVersionedContractByHash, gen: JsonGenerator) = {
      gen.writeStartObject
      gen.writeFieldName("StoredVersionedContractByHash")
      gen.writeStartObject
      gen.writeFieldName("hash")
      gen.writeString(HexUtils.toHex(storedVersionedContractByHash.hash.get.hash).get)
      gen.writeFieldName("version")
      storedVersionedContractByHash.version match {
        case Some(_) => gen.writeRawValue("Some("+storedVersionedContractByHash.version.get+")")// .writeNumber(storedVersionedContractByHash.version.get)
        case None => gen.writeRawValue("None")
      }
      gen.writeFieldName("entry_point")
      gen.writeString(storedVersionedContractByHash.entry_point)
      serializeArgs(storedVersionedContractByHash, gen)
      gen.writeEndObject
      gen.writeEndObject
    }

    /**
     * serialize  storedVersionedContractByName
     *
     * @param modulesbytes
     * @param gen
     */

    def writeStoredVersionedContractByName(storedVersionedContractByName: StoredVersionedContractByName, gen: JsonGenerator) = {
      gen.writeStartObject
      gen.writeFieldName("StoredVersionedContractByName")
      gen.writeStartObject
      gen.writeFieldName("name")
      gen.writeString(storedVersionedContractByName.name)
      gen.writeFieldName("version")
      storedVersionedContractByName.version match {
        case Some(_) => gen.writeRawValue("Some("+storedVersionedContractByName.version.get+")")
        case None => gen.writeRawValue("None")
      }
      gen.writeFieldName("entry_point")
      gen.writeString(storedVersionedContractByName.entry_point)
      serializeArgs(storedVersionedContractByName, gen)
      gen.writeEndObject
      gen.writeEndObject
    }


    /**
     * Serialize DeployNamedArg sequence
     *
     * @param value
     * @param gen
     */
    def serializeArgs(value: DeployExecutable, gen: JsonGenerator) = {
      assert(value != null && !value.args.isEmpty)
      val namedArgs: Seq[DeployNamedArg] = value.args(0)
      gen.writeFieldName("args")
      gen.writeStartArray()
      for (i <- 0 to (namedArgs.size - 1)) {
        gen.writeStartArray()
        gen.writeString(namedArgs(i).asInstanceOf[DeployNamedArg].name)
        gen.getCodec().writeValue(gen, namedArgs(i).asInstanceOf[DeployNamedArg].value)
        gen.writeEndArray()
      }
      gen.writeEndArray()

      /*
      gen.writeFieldName("args")
      gen.getCodec.writeValue(gen, value.args)
      */
    }
  }
}
