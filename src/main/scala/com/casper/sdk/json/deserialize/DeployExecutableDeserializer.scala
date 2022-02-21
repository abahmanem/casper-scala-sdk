package com.casper.sdk.json.deserialize

import com.casper.sdk.crypto.hash.Hash
import com.casper.sdk.domain.deploy.{DeployNamedArg, ModuleBytes, StoredContractByHash, _}
import com.casper.sdk.types.cltypes.CLValue
import com.casper.sdk.util.JsonConverter
import com.fasterxml.jackson.core.{JsonParser, ObjectCodec, TreeNode}
import com.fasterxml.jackson.databind.node.TextNode
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}

import scala.util.{Success, Try}

/**
 * Custom fasterXml Deserializer for DeployExecutable objects
 */
class DeployExecutableDeserializer extends JsonDeserializer[DeployExecutable] {
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): DeployExecutable = Try {
    val codec: ObjectCodec = parser.getCodec
    val treeNode: TreeNode = codec.readTree(parser)
    val it = treeNode.fieldNames()
    var deployexe: DeployExecutable = null
    while (it.hasNext) {
      it.next() match {
        case "ModuleBytes" => {

          deployexe = new ModuleBytes(treeNode.get("ModuleBytes").get("module_bytes").asInstanceOf[TextNode].asText().getBytes, getArgs("ModuleBytes", treeNode))
        }
        case "StoredContractByHash" => {

          deployexe = new StoredContractByHash(Some(new Hash(treeNode.get("StoredContractByHash").get("hash").asInstanceOf[TextNode].asText())),
            treeNode.get("StoredContractByHash").get("entry_point").asInstanceOf[TextNode].asText(),
            getArgs("StoredContractByHash", treeNode))
        }
        case "StoredContractByName" => {
          deployexe = new StoredContractByName(treeNode.get("StoredContractByName").get("name").asInstanceOf[TextNode].asText(),
            treeNode.get("StoredContractByName").get("entry_point").asInstanceOf[TextNode].asText(),
            getArgs("StoredContractByName", treeNode))
        }
        case "StoredVersionedContractByHash" => {
          deployexe = new StoredVersionedContractByHash(Some(new Hash(treeNode.get("StoredVersionedContractByHash").get("hash").asInstanceOf[TextNode].asText())),
            if (treeNode.get("StoredVersionedContractByHash").get("version").asInstanceOf[TextNode].asText().isEmpty) None else
              Some(treeNode.get("StoredVersionedContractByHash").get("version").asInstanceOf[TextNode].asText().toInt),
            treeNode.get("StoredVersionedContractByHash").get("entry_point").asInstanceOf[TextNode].asText(),
            getArgs("StoredVersionedContractByHash", treeNode))
        }
        case "StoredVersionedContractByName" => {
          deployexe = new StoredVersionedContractByName(treeNode.get("StoredVersionedContractByName").get("name").asInstanceOf[TextNode].asText(),
            if (treeNode.get("StoredVersionedContractByName").get("version").asInstanceOf[TextNode].asText().isEmpty) None else
              Some(treeNode.get("StoredVersionedContractByName").get("version").asInstanceOf[TextNode].asText().toInt),
            treeNode.get("StoredVersionedContractByName").get("entry_point").asInstanceOf[TextNode].asText(),
            getArgs("StoredVersionedContractByName", treeNode))
        }
        case "Transfer" => {
          deployexe = new DeployTransfer(getArgs("Transfer", treeNode))
        }
      }
    }
    deployexe
  }
  match {
    case Success(x) => x
    case _ => null
  }

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
      deployArgsSeq = deployArgsSeq :+ new DeployNamedArg(subArg.get(0).asInstanceOf[TextNode].asText(), JsonConverter.fromJson[CLValue](subArg.get(1).toString).get)
    }
    Seq(deployArgsSeq)
  }
}
