package com.casper.sdk.json.deserialize

import com.fasterxml.jackson.core.{JsonParser, ObjectCodec, TreeNode}
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}
import com.casper.sdk.types.cltypes.CLType
import com.fasterxml.jackson.databind.node.TextNode

import java.io.IOException
import scala.util.{Failure, Success, Try}

/**
 * Custom fasterXml Deserializer for CLType enum
 */

class CLTypeDeserialiser extends JsonDeserializer[CLType] {
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): CLType = Try {
      val codec: ObjectCodec = parser.getCodec
      val treeNode: TreeNode = codec.readTree(parser)

      treeNode match {
        case treeNode: TextNode => CLType.valueOf(treeNode.asInstanceOf[TextNode].asText())
        case _ => CLType.valueOf(treeNode.fieldNames.next)
      }
    }
   match {
    case Success(x) => x
    case _ => null
  }
}


/*

val codec: ObjectCodec = parser.getCodec
val treeNode: TreeNode = codec.readTree(parser)

treeNode match {
  case treeNode: TextNode => CLType.valueOf(treeNode.asInstanceOf[TextNode].asText())
  case _ => CLType.valueOf(treeNode.fieldNames.next)
}
}
}
*/