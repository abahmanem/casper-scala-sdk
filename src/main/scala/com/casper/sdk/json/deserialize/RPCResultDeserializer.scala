package com.casper.sdk.json.deserialize

import com.casper.sdk.types.cltypes.{CLPublicKey, CLValue}
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer, JsonNode}

import scala.reflect.ClassTag
import com.casper.sdk.domain._
import com.casper.sdk.domain.{AuctionState, EraSummary}
import com.casper.sdk.domain.deploy.Deploy
import com.casper.sdk.rpc.{RPCError, RPCResult}
import com.casper.sdk.util.JsonConverter
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.util.JSONPObject

import java.io.IOException

class RPCResultDeserializer extends JsonDeserializer[RPCResult[?]] {
  @throws[IOException]
  override def deserialize(parser: JsonParser, ctx: DeserializationContext): RPCResult[?] = {
    val codec = parser.getCodec
    val node = codec.readTree(parser).asInstanceOf[ObjectNode]
    val err = Option(node.findValue("error"))
    val peers = Option(node.findValue("peers"))
    val state_root_hash = Option(node.findValue("state_root_hash"))
    val auction_state = Option(node.findValue("auction_state"))
    val deploy = Option(node.findValue("deploy"))
    val transfers = Option(node.findValue("transfers"))
    val era_summary = Option(node.findValue("era_summary"))
    val block = Option(node.findValue("block"))

    //error
    if (err.isDefined) {
      new RPCResult[Peer](null, JsonConverter.fromJson[RPCError](err.get.toString))
    }
    //block
    else if (block.isDefined) {
      new RPCResult(JsonConverter.fromJson[Block](block.get.toString))
    }
    //peers
    else if (peers.isDefined) {
      new RPCResult[Seq[Peer]](JsonConverter.toList[Peer](peers.get.toString))
    }
    // state_root_hash
    else if (state_root_hash.isDefined && !auction_state.isDefined && !era_summary.isDefined) {
      new RPCResult[String](JsonConverter.fromJson[String](state_root_hash.get.toString))
    }
    //auction_state
    else if (auction_state.isDefined) {
      new RPCResult(JsonConverter.fromJson[AuctionState](auction_state.get.toString))
    }
    //deploy
    else if (deploy.isDefined) {
      new RPCResult[Deploy](JsonConverter.fromJson[Deploy](deploy.get.toString))
    }
    //transferts
    else if (transfers.isDefined) {
      new RPCResult[Seq[Transfer]](JsonConverter.toList[Transfer](transfers.get.toString))
    }
    //era_summary
    else if (era_summary.isDefined) {
      new RPCResult[EraSummary](JsonConverter.fromJson[EraSummary](era_summary.get.toString))
    }
    //nothing
    else {
      null
    }
  }
}