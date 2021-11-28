package com.casper.sdk.json.serialize

import com.casper.sdk.types.cltypes.{CLTypeInfo, CLValue}
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.ObjectCodec
import com.fasterxml.jackson.core.TreeNode
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.node.NumericNode
import com.fasterxml.jackson.databind.node.TextNode


import com.casper.sdk.types.cltypes.CLOptionTypeInfo
import com.casper.sdk.types.cltypes.CLType
import com.casper.sdk.types.cltypes.CLByteArrayInfo
import com.casper.sdk.types.cltypes.CLOptionValue
import java.io.IOException


class CLValueSerializer extends JsonDeserializer[CLValue]{

  
  @throws[IOException]
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): CLValue = {
    val codec : ObjectCodec = parser.getCodec

    val treeNode :  TreeNode = codec.readTree(parser)

    getClValue(treeNode)
  }

  def getClValue(treeNode : TreeNode): CLValue = {

    val bytesNode = treeNode.get("bytes").asInstanceOf[TextNode].asText()
    val clTypeInfo = getCLTypeInfo(treeNode)
    val parsed = getParsed(treeNode.get("parsed"), clTypeInfo)
    val typeNode = treeNode.get("cl_type")



    if (clTypeInfo.isInstanceOf[CLOptionTypeInfo])
      new CLOptionValue(bytesNode, clTypeInfo.asInstanceOf[CLOptionTypeInfo], parsed)
    else
      new CLValue(bytesNode, clTypeInfo, parsed)

  }


  def getCLTypeInfo( typeNode : TreeNode ) : CLTypeInfo ={


    val clType = getClType(typeNode)

    if ( clType == CLType.ByteArray) {
      val sizeNode = typeNode.get(CLType.ByteArray.toString)
      var size = 0
      if (sizeNode.isInstanceOf[NumericNode]) size = sizeNode.asInstanceOf[NumericNode].asInt
      new CLByteArrayInfo(size)
    }
    else if (clType == CLType.Option) {
      val optionNode = typeNode.get(CLType.Option.toString)
      val interType = getCLTypeInfo(optionNode)
      new CLOptionTypeInfo(interType)
    }
    else return new CLTypeInfo(clType)



  }

  def getParsed( typeNode : TreeNode , clTypeInfo : CLTypeInfo ) : Any ={

    if (typeNode.isInstanceOf[TextNode])
      typeNode.asInstanceOf[TextNode].asText
    else if (typeNode.isInstanceOf[NumericNode] && CLType.isNumeric(clTypeInfo.cl_Type))
      typeNode.asInstanceOf[NumericNode].bigIntegerValue
    else
      null
  }

  def  getClType(typeNode : TreeNode)  :  CLType =  {

    if (typeNode.isInstanceOf[TextNode])
      CLType.values.find(_.toString == typeNode.asInstanceOf[TextNode].asText()).get
    else
      CLType.values.find(_.toString == typeNode.fieldNames.next).get
  }



}
