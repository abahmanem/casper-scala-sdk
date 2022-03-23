package com.casper.sdk

import com.casper.sdk.crypto.hash.Hash
import com.casper.sdk.domain._
import com.casper.sdk.domain.deploy._
import com.casper.sdk.rpc.exceptions.RPCException
import com.casper.sdk.rpc.http.HttpRPCService
import com.casper.sdk.rpc.{Method, RPCCommand, RPCError}
import com.casper.sdk.types.cltypes.URef

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
   * @return : List[Peer]
   */
  def getPeers(): Try[Seq[Peer]] = call[Seq[Peer]](Method.INFO_GET_PEERS, Seq.empty)



  /**
   * Retrieves a state root hash at a given block
   *
   * @param blockHash
   * @return state_root_hash_String
   */
  def getStateRootHash(blockHash: String): Try[String] = call[String](Method.STATE_ROOT_HASH, blockHash)

   /**
    * getBlock
    *
    * @param blockHash : Block hash
    * @return :  Block
    */
   def getBlock(blockHash: String): Try[Block] = call[Block](Method.CHAIN_GET_BLOCK, Map("Hash" -> blockHash))

    /**
     * getBlock
     *
     * @param blockHeignt : Block height
     * @return : Block
     */
    def getBlockByHeight(blockHeight: BigInt): Try[Block] = call[com.casper.sdk.domain.Block](Method.CHAIN_GET_BLOCK, Map("Height" -> blockHeight))


    /**
     * getStatus
     *
     * @return : Node Status
     */
    def getStatus(): Try[NodeStatus] = call[NodeStatus](Method.INFO_GET_STATUS, Seq.empty)


    /**
     * getBlockTransfers
     *
     * @param blockHash : Block Hash
     * @return : List of Transferts within a Block
     */
    def getBlockTransfers(blockHash: String): Try[Seq[Transfer]] = call[Seq[Transfer]](Method.CHAIN_GET_BLOCK_TRANSFERTS, Map("Hash" -> blockHash))

    /**
     * getAuctionInfo
     *
     * @param blockHash :  Block Hash
     * @return : Auction infos
     */
    def getAuctionInfo(blockHash: String): Try[AuctionState] = call[AuctionState](Method.STATE_GET_AUCTION_INFO, Seq(blockHash))

    /**
     * getDeploy
     *
     * @param deployHash : deploy Hash
     * @return : Deploy
     */
    def getDeploy(deployHash: String): Try[Deploy] = call[Deploy](Method.INFO_GET_DEPLOY, deployHash)

    /**
     * getEraInfoBySwitchBlock
     *
     * @param blockHash : Switch block Hash
     * @return : EraSummary for the Switch block
     */
    def getEraInfoBySwitchBlock(blockHash: String): Try[EraSummary] = call[EraSummary](Method.CHAIN_GET_ERA_INFO_BY_SWITCH_BLOCK, Map("Hash" -> blockHash))

    /**
     * getStateItem
     *
     * @param blockHash
     * @return StoredValue (CLValue, Account or Contract)
     */
    def getStateItem(stateRootHash: String, key: String, path: Seq[Any] = Seq.empty): Try[StoredValue] = call[StoredValue](Method.STATE_GET_ITEM, stateRootHash, key, path)

    /**
     * getBalance
     *
     * @param blockHash
     * @return : Balance of a URef account
     */
    def getBalance(stateRootHash: String, uref: Option[URef]): Try[Long] = {
     if (uref.isDefined)
      call[Long](Method.STATE_GET_BALANCE, stateRootHash, uref.get.format)
     else
      Failure( new IllegalArgumentException("Invalid URef parameter"))

    }

    /**
     * getDictionaryItem
     *
     * @param stateRootHash : State Root Hash
     * @param itemKey       :  dictionary Item Key
     * @param uref          : seed Uref
     * @return StoredValue
     */
    def getDictionaryItem(stateRootHash: String, itemKey: String, uref: String): Try[StoredValue] =
      call[StoredValue](Method.STATE_GET_DICTIONARY_ITEM, stateRootHash, Map("URef" -> Map("dictionary_item_key" -> itemKey, "seed_uref" -> uref)))

    /**
     * getRpcSchema
     *
     * @return : Json String with RPCSchema infos
     */
    def getRpcSchema(): Try[String] = call[String](Method.RPC_SCHEMA, Seq.empty)

    /**
     * put deploy
     *
     * @param deploy
     * @return Hash
     */
    def putDeploy(deploy: Deploy): Try[Hash] = call[Hash](Method.ACCOUNT_PUT_DEPLOY, deploy)

}
