package com.casper.sdk.json.deserialize

import com.casper.sdk.domain.{AuctionState, Block, EraSummary, NodeStatus, Peer, StoredValue, Transfer}
import com.casper.sdk.domain.deploy.Deploy
import com.casper.sdk.util.{HexUtils, JsonConverter}
import com.casper.sdk.rpc.RPCResult
import com.casper.sdk.types.cltypes.CLType
import org.scalatest.funsuite.AnyFunSuite

import scala.io.Source

/**
 * RPCResultDeserializerTest
 */
class RPCResultDeserializerTest extends AnyFunSuite {

  test("RPCResultDeserializer test with getPeers") {
    val peersJson = Source.fromURL(getClass.getResource("/json/deserialize/peers.json")).mkString
    val peers = JsonConverter.fromJson[RPCResult[Seq[Peer]]](peersJson).get.result.get
    assert(peers.size == 352)
    assert(peers(0).address == "18.163.249.168:35000")
  }

  test("RPCResultDeserializer test with getStateRootHash") {
    val stateroothash = """{"rpc_call":"chain_get_state_root_hash","jsonrpc":"2.0","id":1,"result":{"api_version":"1.4.3","state_root_hash":"ac7bDeE1f82e0949C2A5A9C469E89FC3bCeF12B73B87840924FedC87Af0b9fF3"}}"""
    val stateroot_hash = JsonConverter.fromJson[RPCResult[String]](stateroothash).get.result.get
    assert(stateroot_hash.toLowerCase == "ac7bDeE1f82e0949C2A5A9C469E89FC3bCeF12B73B87840924FedC87Af0b9fF3".toLowerCase)
  }

  test("RPCResultDeserializer test with getBlock") {
    val jsonBlock = Source.fromURL(getClass.getResource("/json/deserialize/block.json")).mkString
    val block = JsonConverter.fromJson[RPCResult[Block]](jsonBlock).get.result.get
    assert(block.hash.toLowerCase == "197C8CEf3a3e34A6485a3a09fbb588EC062661d5681774487b6Fd37D1C4eab85".toLowerCase)
    assert(block.header.parent_hash.toLowerCase == "B9Df9E5E3eE805c24d5A8daB81bc74f45b8551A5E8BC2DcA4C95692d1aC09036".toLowerCase)
    assert(block.body.proposer.toLowerCase == "016f6eD70E4a5aCEc750dc087674e5dE2ad7B6d9595945c4059C5ca1a47d4DD3Ab".toLowerCase)
  }

  test("RPCResultDeserializer test with getBlockByHeight") {
    val jsonBlock = Source.fromURL(getClass.getResource("/json/deserialize/block_height.json")).mkString
    val block = JsonConverter.fromJson[RPCResult[Block]](jsonBlock).get.result.get
    assert(block.hash.toLowerCase == "c06e5d3A243BFAFac648C48f064d1BdEAf71824869f03a44692c03f6F2A45981".toLowerCase)
    assert(block.header.parent_hash.toLowerCase == "E06488Bcbd71Df03d65Ed8c1559CDfF6ad8EAE85Ac25F1e4Ac377ee2B30397cF".toLowerCase)
    assert(block.body.proposer.toLowerCase == "01b5d00a38E1783345fFE0bFa8423e026b76480683E0b19966ee47c7f68a827c00".toLowerCase)
  }

  test("RPCResultDeserializer test with getStatus") {
    val statusJson = Source.fromURL(getClass.getResource("/json/deserialize/node_status.json")).mkString
    val status = JsonConverter.fromJson[RPCResult[NodeStatus]](statusJson).get.result.get
    assert(status.starting_state_root_hash.toLowerCase == "9C2bef605eEb9aDCFAe53CEF3de1f6B46EB464875e793d1ec6D28c60193d9384".toLowerCase)
    assert(status.chainspec_name == "casper-test")
    assert(status.last_added_block_info.hash.toLowerCase == "d9BfF1024c00A9DA43d9CF4C9565794b71607806eC07fCDBFfe0b2B4d144c3e4".toLowerCase)
  }


  test("RPCResultDeserializer test with getBlockTransfers") {
    val blockTransfers =
      """{"rpc_call":"chain_get_block_transfers","jsonrpc":"2.0","id":1,"result":{"api_version":"1.4.3","block_hash":"9F2D38d6b6d139DAA70fEF85FE7f1907A1ce055ca87e260995E4B84D161d42E5","transfers":[{"deploy_hash":"2aaE8667080ACD7FBd7A9B232734F4d08708B70915426B639f52f54de3c73FFe","from":"account-hash-73CA72105980509c37dEEAE8de8138a4C1BC904BaD01302442E7Dc06acaE17eA","to":"account-hash-7f4bf39A311a7538d8C91BB86C71DF774023e16bc4a70ab7e4e8AE77DbF2Ef53","source":"uref-F7f5355Dcb6eB31FE29c6852E04dF63bAF91f3391f84af4a438aB0Ae49F7a5c8-007","target":"uref-C051e7EC16e08Def8b556F9CD0E18FE701C89dC0cED3DAd7b65107285da198DD-004","amount":"899900000000","gas":"0","id":1641305778025}]}}
        |""".stripMargin
    val transfers = JsonConverter.fromJson[RPCResult[Seq[Transfer]]](blockTransfers).get.result.get
    assert(transfers.size == 1)
    assert(transfers(0).deploy_hash.toLowerCase == "2aaE8667080ACD7FBd7A9B232734F4d08708B70915426B639f52f54de3c73FFe".toLowerCase)
    assert(transfers(0).from.format.toLowerCase == "account-hash-73CA72105980509c37dEEAE8de8138a4C1BC904BaD01302442E7Dc06acaE17eA".toLowerCase)
    assert(transfers(0).to.format.toLowerCase == "account-hash-7f4bf39A311a7538d8C91BB86C71DF774023e16bc4a70ab7e4e8AE77DbF2Ef53".toLowerCase)
    assert(transfers(0).source.format.toLowerCase == "uref-F7f5355Dcb6eB31FE29c6852E04dF63bAF91f3391f84af4a438aB0Ae49F7a5c8-007".toLowerCase)
  }


  test("RPCResultDeserializer test with getAuctionInfo") {
    val auctionInfoJson = Source.fromURL(getClass.getResource("/json/deserialize/auction.json")).mkString
    val auction = JsonConverter.fromJson[RPCResult[AuctionState]](auctionInfoJson).get.result.get
    assert(auction.bids.size == 1535)
    assert(auction.block_height == "427968")
    assert(auction.era_validators.size == 2)
    assert(auction.state_root_hash.toLowerCase == "836D72BC036958dF8b71dFAe04283Ff74ec5bDCa66fcA996E1f363BB9B3946a7".toLowerCase)
  }


  test("RPCResultDeserializer test with getDeploy") {
    val deployJson = Source.fromURL(getClass.getResource("/json/deserialize/deploy.json")).mkString
    val deploy = JsonConverter.fromJson[RPCResult[Deploy]](deployJson).get.result.get
    assert(deploy.approvals.size == 1)
    assert(HexUtils.toHex(deploy.hash.get.hash).get.toLowerCase == "a11ca2157b073062c3d2f2c918e6371dd33658622443bcd2fa2f7d6e2105959f".toLowerCase())
    assert(deploy.session.getClass.getSimpleName == "ModuleBytes")
    assert(deploy.payment.getClass.getSimpleName == "ModuleBytes")

  }

  test("RPCResultDeserializer test with getEraInfoBySwitchBlock") {
    val eraJson = Source.fromURL(getClass.getResource("/json/deserialize/era_summary.json")).mkString
    val era = JsonConverter.fromJson[RPCResult[EraSummary]](eraJson).get.result.get
    assert(era.era_id == 2974)
    assert(era.state_root_hash == "c1A62d5DeB74d3fEAfeCd1EEa526941edd0264895EB8E516474108D4EA4D7D21")
  }

  test("RPCResultDeserializer test with getStateItem") {
    val storedValueJson = Source.fromURL(getClass.getResource("/json/deserialize/state_item.json")).mkString
    val storedValue = JsonConverter.fromJson[RPCResult[StoredValue]](storedValueJson).get.result.get
    assert(storedValue.Contract != null)
    assert(storedValue.Contract.contract_wasm_hash.toLowerCase == "contract-wasm-8D1c5ab95baaa77c302232a00FE1C6cF6975ffD6430a9ECd87EC5266280BB18D".toLowerCase)
    assert(storedValue.Contract.entry_points(0).name == "allowance")
    assert(storedValue.Contract.entry_points(0).args(0).cl_type.toString == "Key")
  }

  test("RPCResultDeserializer test with getBalance") {
    val balanceJson = Source.fromURL(getClass.getResource("/json/deserialize/account_balance.json")).mkString
    val balance = JsonConverter.fromJson[RPCResult[Long]](balanceJson).get.result.get
    assert(balance.toLong == 869077209920L)
  }

  test("RPCResultDeserializer test with getDictionaryItem") {
    val storedValueJson = Source.fromURL(getClass.getResource("/json/deserialize/dictionnary_item.json")).mkString
    val storedValue = JsonConverter.fromJson[RPCResult[StoredValue]](storedValueJson).get.result.get
    assert(storedValue.CLValue != null)
    assert(storedValue.CLValue.cl_infoType.cl_Type == CLType.String)
    assert(storedValue.CLValue.parsed == "https://caspercommunity.io")
  }
}