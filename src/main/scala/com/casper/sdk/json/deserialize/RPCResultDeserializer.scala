package com.casper.sdk.json.deserialize

import com.casper.sdk.types.cltypes.{CLPublicKey, CLValue}
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer, JsonNode}

import scala.reflect.ClassTag
import com.casper.sdk.domain._
import com.casper.sdk.rpc.Method
import com.casper.sdk.domain.{AuctionState, EraSummary}
import com.casper.sdk.domain.deploy.Deploy
import com.casper.sdk.rpc.{RPCError, RPCResult}
import com.casper.sdk.util.JsonConverter
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.util.JSONPObject
import com.casper.sdk.crypto.hash.Hash
import scala.util.{Try, Success, Failure}

/**
 * Custom fasterXml Deserializer for RCPResult Object
 */
class RPCResultDeserializer extends JsonDeserializer[RPCResult[?]] {
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): RPCResult[?] = Try {
    val codec = parser.getCodec
    val node = codec.readTree(parser).asInstanceOf[ObjectNode]
    //error node if any
    val err = Option(node.findValue("error"))

    err match {
      case Some(x) if x != null => new RPCResult[Peer](null, JsonConverter.fromJson[RPCError](x.toString).get)
      case Some(_) => null
      case None => {
        val rpc_call = node.findValue("rpc_call").asText()
        Method.values.find(_.name == rpc_call).get
        match {
          case Method.INFO_GET_PEERS => new RPCResult[Seq[Peer]](JsonConverter.toList[Peer](node.findValue("peers").toString))
          case Method.STATE_GET_BALANCE => new RPCResult[Long](JsonConverter.fromJson[Long](node.findValue("balance_value").asText).get)
          case Method.CHAIN_GET_BLOCK => new RPCResult[Block](JsonConverter.fromJson[Block](node.findValue("block").toString).get)
          case Method.CHAIN_GET_BLOCK_TRANSFERTS => new RPCResult[Seq[Transfer]](JsonConverter.toList[Transfer](node.findValue("transfers").toString))
          case Method.STATE_ROOT_HASH => new RPCResult[String](JsonConverter.fromJson[String](Option(node.findValue("state_root_hash")).get.toString).get)
          case Method.INFO_GET_DEPLOY => new RPCResult[Deploy](JsonConverter.fromJson[Deploy](node.findValue("deploy").toString).get)
          case Method.INFO_GET_STATUS => new RPCResult[NodeStatus](JsonConverter.fromJson[NodeStatus](node.findValue("result").toString).get)
          case Method.CHAIN_GET_ERA_INFO_BY_SWITCH_BLOCK => new RPCResult[EraSummary](JsonConverter.fromJson[EraSummary](node.findValue("era_summary").toString).get)
          case Method.STATE_GET_AUCTION_INFO => new RPCResult(JsonConverter.fromJson[AuctionState](node.findValue("auction_state").toString).get)
          case Method.STATE_GET_ITEM | Method.STATE_GET_DICTIONARY_ITEM => new RPCResult(JsonConverter.fromJson[StoredValue](node.findValue("stored_value").toString).get)
          case Method.ACCOUNT_PUT_DEPLOY => new RPCResult[Hash](JsonConverter.fromJson[Hash](node.findValue("deploy_hash").toString).get)
          case Method.RPC_SCHEMA => new RPCResult(node.toString.patch(1, "", 26)) //get reid of the rpc_call attribute
        }
      }
    }
  }
  match {
    case Success(x) => x
    case _ => null
  }
}