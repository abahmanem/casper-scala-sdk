package com.casper.sdk

import cats.Id
import com.casper.sdk.domain.*
import com.casper.sdk.domain.deploy.*
import com.casper.sdk.rpc.http.HttpRPCService
import com.casper.sdk.rpc.{Method, RPCCommand}
import com.casper.sdk.util.IdInstance
import com.casper.sdk.util.implicits

/**
 * Casper SDK main class
 * @param url
 * @param id
 */

class CasperSdk(url: String)(implicit id: IdInstance) extends RPCCommand(new HttpRPCService(url)) {

  //Milestone #1

  /**
   * get peers list
   * @return : List[Peer]
   */
  def getPeers(): Seq[Peer] = call[Seq[Peer]](Method.INFO_GET_PEERS, Seq.empty)
  /**
   * Retrieves a state root hash at a given block
   * @param blockHash
   * @return state_root_hash_String
   */

  def getStateRootHash(blockHash: String): String = call[String](Method.STATE_ROOT_HASH, blockHash)

  //-----------------------------------------------------------------------------------------------------------------------------//
  //Milestone #2

  //block_identifier = Hash
  def getBlock(blockHash: String): Block = call[Block](Method.CHAIN_GET_BLOCK, Map("Hash" -> blockHash))

  //block_identifier = Height
  def getBlockByHeight(blockHeignt: BigInt): com.casper.sdk.domain.Block = call[com.casper.sdk.domain.Block](Method.CHAIN_GET_BLOCK, Map("Height" -> blockHeignt))

  //info_get_status
  def getStatus(): NodeStatus = call[NodeStatus](Method.INFO_GET_STATUS, Seq.empty)

  //chain_get_block_transfers
  def getBlockTransfers(blockHash: String): Seq[Transfer] = call[Seq[Transfer]](Method.CHAIN_GET_BLOCK_TRANSFERTS, Map("Hash" -> blockHash))

  //state_get_auction_info
  def getAuctionInfo(blockHash: String): AuctionState = call[AuctionState](Method.STATE_GET_AUCTION_INFO, Seq(blockHash))

  //info_get_deploy
  def getDeploy(deployHash: String): Deploy = call[Deploy](Method.INFO_GET_DEPLOY, deployHash)


  //chain_get_era_info_by_switch_block
  def getEraInfoBySwitchBlock(blockHash: String) : EraSummary = call[EraSummary](Method.CHAIN_GET_ERA_INFO_BY_SWITCH_BLOCK, Map("Hash" -> blockHash))

//state_get_item
//state_get_dictionary_item
//state_get_balance
  


  //Milestone #3
  // def put_deploy





}


