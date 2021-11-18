package com.casper.sdk

import _root_.cats.Id
import _root_.cats.implicits.*
import com.casper.sdk.*
import com.casper.sdk.domain.*
import com.casper.sdk.rpc.http.HttpRPCService
import com.casper.sdk.rpc.result.*
import com.casper.sdk.rpc.{Method, RPCCommand}
import com.casper.sdk.util.IdInstance
import com.casper.sdk.util.implicits.*

import scala.reflect.ClassTag
import scala.reflect.runtime.universe.*

/**
 *
 * @param url
 * @param id
 */

class CasperSdk(url: String)(implicit id: IdInstance) extends RPCCommand(new HttpRPCService(url)) {

  //Milestone #1

  def info_get_peers(): List[Peer] = call[PeersResult](Method.INFO_GET_PEERS, Seq.empty).peers

  /**
   * Retrieves a state root hash at a given block
   * @param blockHash
   * @return
   */
  def state_root_hash(blockHash: String): String = call[StateRootHashResult](Method.STATE_ROOT_HASH, blockHash).state_root_hash

  //-----------------------------------------------------------------------------------------------------------------------------//
  //Milestone #2

  //block_identifier = Hash
  def chain_get_block(blockHash: String): com.casper.sdk.domain.Block = call[BlockResult](Method.CHAIN_GET_BLOCK, Map("Hash" -> blockHash)).block

  //block_identifier = Height
  def chain_get_block_By_Height(blockHeignt: BigInt): com.casper.sdk.domain.Block = call[BlockResult](Method.CHAIN_GET_BLOCK, Map("Height" -> blockHeignt)).block

  def info_get_status(): NodeStatus = call[NodeStatus](Method.INFO_GET_STATUS, Seq.empty)

  def chain_get_block_transfers(blockHash: String): Seq[Transfer] = call[BlockTransfertResult](Method.CHAIN_GET_BLOCK_TRANSFERTS, Map("Hash" -> blockHash)).transfers

  def state_get_auction_info(blockHash: String): AuctionState = call[AuctionStateResult](Method.STATE_GET_AUCTION_INFO, Seq(blockHash)).auction_state

  // def state_get_item
  // def state_get_dictionary_item
  // def state_get_balance
  // def info_get_deploy

  //Milestone #3
  // def put_deploy

}


