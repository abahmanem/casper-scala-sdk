package com.casper.sdk

import _root_.cats.Id
import _root_.cats.implicits.*
import com.casper.sdk.*
import com.casper.sdk.domain.*
import com.casper.sdk.rpc.{Method, RPCCalls}
import com.casper.sdk.rpc.result.*
import com.casper.sdk.util.IdInstance
import com.casper.sdk.util.implicits.*

import scala.reflect.ClassTag
import scala.reflect.runtime.universe.*


class CasperSdk(url: String)(implicit id: IdInstance) extends RPCCalls(url) {

  def get_info_get_peers(): Peers = invokeSync[Peers](Method.INFO_GET_PEERS, Seq.empty)

  def get_state_root_hash(blockHash: String): StateRootHash = invokeSync[StateRootHash](Method.STATE_ROOT_HASH, blockHash)

  /*
    //Milestone #1
    def get_info_get_peers(data: Seq[String]) :  F[GetPeersResult] = call("info_get_peers", data)
    def get_chain_get_state_root_hash(data: Seq[String]) :  F[GetStateRootHashResult] = call("chain_get_state_root_hash", data)
    //Milestone #2
    // Chad
    // def chain_get_block(data: Seq[String]) :  F[GetBlockResult] = call("info_get_deploy", data)
    // def chain_get_block(data: Seq[String]) :  F[GetBlockResult] = call("info_get_status", data)
    // def chain_get_block(data: Seq[String]) :  F[GetBlockResult] = call("chain_get_block_transfers", data)
    // def chain_get_block(data: Seq[String]) :  F[GetBlockResult] = call("chain_get_block", data)
    // def chain_get_block(data: Seq[String]) :  F[GetBlockResult] = call("chain_get_era_info_by_switch_block", data)
    // Mika
    def state_get_item(data: Seq[String]) :  F[StateGetItemResult] = call("state_get_item", data)
    def state_get_dictionary_item(data: Seq[Any]) :  F[StateGetDictionaryItemResult] = call("state_get_dictionary_item", data)
    def state_get_balance(data: Seq[String]) :  F[StateGetBalanceResult] = call("state_get_balance", data)
    def state_get_auction_info(data: Seq[String]) :  F[StateGetAuctionInfoResult] = call("state_get_auction_info", data)
  */
}


