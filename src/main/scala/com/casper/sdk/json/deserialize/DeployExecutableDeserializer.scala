package com.casper.sdk.json.deserialize

import com.casper.sdk.domain.deploy.*
import com.casper.sdk.domain.deploy.{DeployNamedArg, ModuleBytes, StoredContractByHash}
import com.casper.sdk.util.JsonConverter
import com.fasterxml.jackson.core.{JsonParser, ObjectCodec, TreeNode}
import com.fasterxml.jackson.databind.node.TextNode
import com.casper.sdk.types.cltypes.CLValue

import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}

import java.io.IOException

/**
 * Custom Deserializer for DeployExecutable
 */
class DeployExecutableDeserializer extends JsonDeserializer[DeployExecutable] {
  @throws[IOException]
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): DeployExecutable = {
    val codec: ObjectCodec = parser.getCodec
    val treeNode: TreeNode = codec.readTree(parser)
    val it = treeNode.fieldNames()
    var deployexe: DeployExecutable = null

    /**
     * extract Seq[Seq[DeployNamedArg]] attribute from Json
     *
     * @param ClassName deployExecutable subclass name
     * @param treeNode
     * @return Seq[Seq[DeployNamedArg]]
     */
    def getArgs(ClassName: String, treeNode: TreeNode): Seq[Seq[DeployNamedArg]] = {
      val args = treeNode.get(ClassName).get("args")
      var deployArgsSeq = Seq[DeployNamedArg]()
      assert(args.isArray)
      for (i <- 0 to (args.size() - 1)) {
        val subArg = args.get(i)
        assert(subArg.isArray)
        deployArgsSeq = deployArgsSeq :+ new DeployNamedArg(subArg.get(0).toString, JsonConverter.fromJson[CLValue](subArg.get(1).toString))
      }
      Seq(deployArgsSeq)
    }

    while (it.hasNext) {
      it.next() match {
        case "ModuleBytes" => {

          deployexe = new ModuleBytes(treeNode.get("ModuleBytes").get("module_bytes").toString.getBytes, getArgs("ModuleBytes", treeNode))
        }
        case "StoredContractByHash" => {
          deployexe = new StoredContractByHash(treeNode.get("StoredContractByHash").get("hash").toString, treeNode.get("StoredContractByHash").get("entry_point").toString,
            getArgs("StoredContractByHash", treeNode))
        }
        case "StoredContractByName" => {
          deployexe = new StoredContractByName(treeNode.get("StoredContractByName").get("name").toString,
            treeNode.get("StoredContractByName").get("entry_point").toString,
            getArgs("StoredContractByName", treeNode))
        }
        case "StoredVersionedContractByHash" => {
          deployexe = new StoredVersionedContractByHash(treeNode.get("StoredVersionedContractByHash").get("hash").toString,
            Some(treeNode.get("StoredVersionedContractByHash").get("version").toString.toInt),
            treeNode.get("StoredVersionedContractByHash").get("entry_point").toString,
            getArgs("StoredVersionedContractByHash", treeNode))
        }
        case "StoredVersionedContractByName" => {
          deployexe = new StoredVersionedContractByHash(treeNode.get("StoredVersionedContractByName").get("name").toString,
            Some(treeNode.get("StoredVersionedContractByName").get("version").toString.toInt),
            treeNode.get("StoredVersionedContractByName").get("entry_point").toString,
            getArgs("StoredVersionedContractByName", treeNode))
        }
        case "Transfer" => {
          deployexe = new DeployTransfer(getArgs("Transfer", treeNode))
        }
        case _ => throw IllegalArgumentException("Not a subtype of  DeployExecutable. Cannot be handled")
      }
    }
    deployexe
  }
}
