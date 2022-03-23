package com.casper.sdk.json.deserialize

import com.casper.sdk.types.cltypes._
import com.fasterxml.jackson.core.{JsonParser, ObjectCodec, TreeNode}
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}
import com.fasterxml.jackson.databind.node.{ArrayNode, NumericNode, ObjectNode, TextNode}
import com.casper.sdk.types.cltypes
import scala.util.{Failure, Success, Try}

/**
 * Custom fasterXml Deserializer for CLValue
 */
class CLValueDeSerializer extends JsonDeserializer[CLValue] {
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): CLValue = Try {
      val codec: ObjectCodec = parser.getCodec
      val treeNode: TreeNode = codec.readTree(parser)
      clValue(treeNode)
    }
    match {
      case Success(x) => x
      case _ => null
    }

  /**
   * build CLValue
   * @param treeNode
   * @return
   */
  def clValue(treeNode: TreeNode): CLValue = {
    val bytesNode = treeNode.get("bytes").asInstanceOf[TextNode].asText()
    val typeNode = treeNode.get("cl_type")
    val clTypeInfo = cLTypeInfo(typeNode)

    val parsedValue =  parsed(treeNode.get("parsed"), clTypeInfo)
    clTypeInfo match {
      case  clTypeInfo :CLOptionTypeInfo  =>  CLOptionValue(bytesNode, clTypeInfo.asInstanceOf[CLOptionTypeInfo], parsedValue)
      case _=> CLValue(bytesNode, clTypeInfo, parsedValue)
    }
  }

  /**
   * get CLType info
   * @param typeNode
   * @return
   */
  def cLTypeInfo(typeNode: TreeNode): CLTypeInfo = {
    val cl_Type = clType(typeNode)
    cl_Type match {
      case CLType.ByteArray => {
        val sizeNode = typeNode.get(CLType.ByteArray.toString)
        var size = 0
        if (sizeNode.isInstanceOf[NumericNode]) size = sizeNode.asInstanceOf[NumericNode].asInt
        new CLByteArrayTypeInfo(size)
      }
      case CLType.Option => {
        val optionNode = typeNode.get(CLType.Option.toString)
        val interType = cLTypeInfo(optionNode)
        new CLOptionTypeInfo(interType)
      }

      case CLType.List => {
        val listNode = typeNode.get(CLType.List.toString)
        val interType = cLTypeInfo(listNode)
        new CLListTypeInfo(interType)
      }
      case _ => {
        new CLTypeInfo(cl_Type)
      }
    }
  }

  /**
   * get parsed value
   * @param typeNode
   * @param clTypeInfo
   * @return
   */
  def parsed(typeNode: TreeNode, clTypeInfo: CLTypeInfo): Any =
    typeNode match {
      case typeNode : TextNode =>  typeNode.asInstanceOf[TextNode].asText
      case typeNode : NumericNode => CLType.isNumeric(clTypeInfo.cl_Type) match {
        case true =>   typeNode.asInstanceOf[NumericNode].bigIntegerValue
        case false =>  null
      }
      case typeNode : ArrayNode =>  typeNode.asInstanceOf[ArrayNode]
      case typeNode : ObjectNode =>  typeNode.asInstanceOf[ObjectNode]

      case _ => null
    }

  /**
   * get CLType
   * @param typeNode
   * @return
   */
  def clType(typeNode: TreeNode): CLType =  typeNode match {
    case typeNode : TextNode =>  CLType.valueOf(typeNode.asInstanceOf[TextNode].asText())
    case _=> CLType.valueOf(typeNode.fieldNames.next)
  }
}
