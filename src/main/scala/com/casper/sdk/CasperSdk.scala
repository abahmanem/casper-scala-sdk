package com.casper.sdk

import com.casper.sdk.rpc.params.*
import com.casper.sdk.crypto.hash.Hash
import com.casper.sdk.domain.*
import com.casper.sdk.domain.deploy.*
import com.casper.sdk.rpc.exceptions.RPCException
import com.casper.sdk.rpc.http.HttpRPCService
import com.casper.sdk.rpc.params.{BlockIdentifier, StateIdentifier, StateRootHashIdentifier}
import com.casper.sdk.rpc.result.{AccountResult, AuctionStateResult, BalanceResult, BlockResult, DeployHashResult, DeployResult, DictionaryItemResult, EraSummaryResult, GlobalStateResult, PeerResult, RPCSchemaResult, StateItemResult, StateRootHashResult, TransferResult}
import com.casper.sdk.rpc.{Method, RPCCommand, RPCError}
import com.casper.sdk.types.cltypes.URef
import io.circe.*
import io.circe.syntax.*

import scala.util.{Failure, Success, Try}
/**
 * Casper SDK main class
 *
 * @param url : RPC endpoint URL
 */

class CasperSdk(url: String) extends RPCCommand(new HttpRPCService(url)) {

  /**
   * get peers list
   *
   * @return : List[PeerResult]
   */

  def getPeers(): Try[PeerResult] = call[PeerResult](Method.INFO_GET_PEERS)


  /**
   * Retrieves a state root hash at a given block
   *
   * @param blockHash
   * @return StateRootHashResult
   */

  def getStateRootHash(blockHash: String): Try[StateRootHashResult] = call[StateRootHashResult](Method.STATE_ROOT_HASH, blockHash.asJson)



  /**
   * getBlock
   *
   * @param blockIdentifier: BlockIdentifier
   * @return :  BlockResult
   */
  def getBlock(blockIdentifier: BlockIdentifier): Try[BlockResult] =  blockIdentifier match {
    case HeightBlockIdentifier(height) => call[BlockResult](Method.CHAIN_GET_BLOCK, Map("Height" -> height).asJson)
    case HashBlockIdentifier(hash) => call[BlockResult](Method.CHAIN_GET_BLOCK, Map("Hash" -> hash).asJson)
    case _=> call[BlockResult](Method.CHAIN_GET_BLOCK, "".asJson)
  }



  /**
   * getStatus
   *
   * @return : Node Status
   */
  def getStatus(): Try[NodeStatus] = call[NodeStatus](Method.INFO_GET_STATUS)


  /**
   * getBlockTransfers
  *
  * @param blockHash : Block Hash
  * @return : List of TransferResult within a Block
  */
  def getBlockTransfers(blockHash: String): Try[TransferResult] = call[TransferResult](Method.CHAIN_GET_BLOCK_TRANSFERS, Map("Hash" -> blockHash).asJson)



  /**
   * getAuctionInfo
   *
   * @param blockHash :  Block Hash
   * @return : AuctionStateResult
   */
  def getAuctionInfo(blockIdentifier: BlockIdentifier): Try[AuctionStateResult] = blockIdentifier match {
    case HeightBlockIdentifier(height) => call[AuctionStateResult](Method.STATE_GET_AUCTION_INFO, Map("Height" -> height).asJson)
    case HashBlockIdentifier(hash) => call[AuctionStateResult](Method.STATE_GET_AUCTION_INFO, Map("Hash" -> hash).asJson)
    case _ => call[AuctionStateResult](Method.STATE_GET_AUCTION_INFO, "".asJson)
  }
  
  /**
   * getDeploy
   *
   * @param deployHash : deploy Hash
   * @return : DeployResult
   */
  def getDeploy(deployHash: String): Try[DeployResult] = call[DeployResult](Method.INFO_GET_DEPLOY, deployHash.asJson)


  /**
   * getEraInfoBySwitchBlock
   *
   * @param blockHash : Switch block Hash
   * @return : EraSummaryResult
   */
  def getEraInfoBySwitchBlock(blockHash: String): Try[EraSummaryResult] = call[EraSummaryResult](Method.CHAIN_GET_ERA_INFO_BY_SWITCH_BLOCK, Map("Hash" -> blockHash).asJson)


  /**
   * getBalance
   *
   * @param blockHash
   * @return :BalanceResult
   */
  def getBalance(stateRootHash: String, purse_uref: String): Try[BalanceResult] = call[BalanceResult](Method.STATE_GET_BALANCE, stateRootHash.asJson, purse_uref.asJson)
 
  /**
   * getDictionaryItem
   *
   * @param stateRootHash : State Root Hash
   * @param itemKey       :  dictionary Item Key
   * @param uref          : seed Uref
   * @return DictionaryItemResult
   */
  def getDictionaryItem(stateRootHash: String, itemKey: String, uref: String): Try[DictionaryItemResult] = call[DictionaryItemResult](Method.STATE_GET_DICTIONARY_ITEM, stateRootHash.asJson, Map("URef" -> Map("dictionary_item_key" -> itemKey, "seed_uref" -> uref)).asJson)


  /**
   * state_get_account_info
   *
   * @return AccountResult
   */
  def getAccountInfo(publicKey: String, blockIdentifier: BlockIdentifier): Try[AccountResult] = blockIdentifier match {
    case HeightBlockIdentifier(height) => call[AccountResult](Method.STATE_GET_ACCOUNT_INFO, publicKey.asJson, Map("Height" -> height).asJson)
    case HashBlockIdentifier(hash) => call[AccountResult](Method.STATE_GET_ACCOUNT_INFO, publicKey.asJson, Map("Hash" -> hash).asJson)
  }


  /**
   * query_global_state
   *
   * @return : GlobalStateResult
   */
  def queryGlobalState(stateIdentifier: StateIdentifier, key: String, path: Seq[Json] = Seq.empty): Try[GlobalStateResult] = stateIdentifier match {
    case StateRootHashIdentifier(stateRootHash) => call[GlobalStateResult](Method.QUERY_GLOBAL_STATE, Map("StateRootHash" -> stateRootHash).asJson , key.asJson, path.asJson)
    case HashBlockIdentifier(hash) => call[GlobalStateResult](Method.QUERY_GLOBAL_STATE, Map("BlockHash" -> hash).asJson , key.asJson, path.asJson)
  }



  /**
   * getRpcSchema
   *
   * @return : RPCSchemaResult : Json String with RPCSchema infos
   */
  def getRpcSchema(): Try[RPCSchemaResult] = call[RPCSchemaResult](Method.RPC_SCHEMA)


  /**
   * put deploy
   *
   * @param deploy
   * @return DeployHashResult
   */
  def putDeploy(deploy: Deploy): Try[DeployHashResult] = call[DeployHashResult](Method.ACCOUNT_PUT_DEPLOY, deploy.asJson)

}
