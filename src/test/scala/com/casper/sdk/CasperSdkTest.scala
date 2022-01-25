package com.casper.sdk

import com.casper.sdk.CasperSdk
import com.casper.sdk.crypto.hash.Blake2b256
import com.casper.sdk.domain._
import com.casper.sdk.domain.deploy._
import com.casper.sdk.rpc.exceptions._
import com.casper.sdk.types.cltypes.{CLPublicKey, CLType, CLValue, _}
import com.casper.sdk.util.implicits.idInstance
import com.casper.sdk.util.{HexUtils, implicits}
import org.scalatest.funsuite.AnyFunSuite

/**
 * Scala Casper SDK Client Test class
 * TODO : use local node : https://github.com/casper-network/casper-node/blob/release-1.4.1/client/README.md
 */

class CasperSdkTest extends AnyFunSuite {

  test("RPC Call to a non existing node (http://1.2.3.4:7777/rpc) Throws an RPCIOException") {
    val caught =
      intercept[RPCIOException] {
        val sdk = new CasperSdk("http://1.2.3.4:7777/rpc")
        val peers = sdk.getPeers()
      }
    assert(caught.getMessage == null)
  }

  val client = new CasperSdk("http://65.21.227.180:7777/rpc")

  /**
   * Test  getPeers
   */
  test("Testnet Network Peers list is not empty") {
    val peers = client.getPeers()
    info("Peers List size : " + peers.size)
    assert(!peers.isEmpty)
  }

  /**
   * Test  getStateRootHash with no parameter
   */
  test("Get state root hash with no blockHash parameter, state root hash is not empty and is 64 characters long") {
    val stateRootHash = client.getStateRootHash("")
    info("state root hash : " + stateRootHash)
    assert(!stateRootHash.isEmpty)
    assert(stateRootHash.length == 64)
  }

  /**
   * Test  getStateRootHash with a block hash paramater
   */
  test("Get state root hash with a blockHash parameter ,state root hash is not empty and is 64 character  long") {
    val stateRootHash = client.getStateRootHash("7ed13ec0f9b1955069fe96e6441fff700e6baed36e8cb99400da0449fae6c95c")
    info("state root hash : " + stateRootHash)
    assert(!stateRootHash.isEmpty)
    assert(stateRootHash.length == 64)
  }

  /**
   * Test Get Block using Block hash parameter
   */

  test("Get Block using Block hash parameter") {
    val block = client.getBlock("74dce8911A3EDf0f872dC11F0a63Ca9fE1b55b7188a9Feaaf431518bF9c508B4")
    info("block  hash : " + block.hash)
    assert(!block.body.proposer.isEmpty)

  }

  /**
   * Test Get Block using empty Block hash parameter
   */

  test("Get Block using an empty Block hash parameter, retrieves the latest known block hash") {
    val block = client.getBlock("")
    info("block  hash : " + block.hash)
    assert(!block.body.proposer.isEmpty)

  }


  /**
   * Test Get Block using non existing Block hash parameter
   */

  test("Get Block using non existing Block hash parameter, retrieves the latest known block hash") {
    val block = client.getBlock("2d1fed7766ffb9d7c9bb0c39d00701c377281132044bd3278b039925eeb3ef")
    info("block  hash : " + block.hash)
    assert(!block.body.proposer.isEmpty)
  }

  /**
   * Test Get Block using Block height parameter
   */

  test("Get Block using Block height parameter") {
    val block = client.getBlockByHeight(371608)
    assert(block.hash.toLowerCase == "2aCa74CF33F6aCe634eD82aC88e597d55920Ff1dE8e596Dd77d9558736EF570d".toLowerCase)
    assert(!block.body.proposer.isEmpty)
  }


  /**
   * Test Get Block using non existing Block height parameter
   */
  test("Get Block using non existing Block height  parameter") {
    val caught: RPCException = intercept[RPCException] {
      val block = client.getBlockByHeight(8745812)
    }
    assert(caught.getMessage == "An error occured when invoking RPC method: chain_get_block with params: " +
      "ArraySeq(Map(Height -> 8745812)). RPC error code: -32001 , RPC error message: block not known")
  }


  /**
   * Test Get Deploy using a Deploy hash parameter
   */
  test("Get Deploy using a Deploy hash parameter") {
    val deploy = client.getDeploy("5545207665f6837F44a6BCC274319280B73a6f0997F957A993e60f878A736678")
    info("deploy  header is not null")
    assert(deploy.header != null)
    info("This deploy  has a ModuleBytes paiement ")
    assert(deploy.payment.getClass.getSimpleName == "ModuleBytes")
    info("This deploy  has a StoredContractByHash as session")
    assert(deploy.session.getClass.getSimpleName == "StoredContractByHash")
  }

  /**
   * Test Get Deploy using an empty Deploy hash parameter
   */
  /*
   {
     "jsonrpc": "2.0",
     "id": 1,
     "error": {
       "code": -32602,
       "message": "Invalid params",
       "data": null
     }
   }*/

  test("Get Deploy using an empty Deploy hash parameter, throws RPCException with message: Invalid params ") {
    val caught: RPCException = intercept[RPCException] {
      val deploy = client.getDeploy("")
    }
    assert(caught.getMessage == "An error occured when invoking RPC method: info_get_deploy with params: ArraySeq(). " +
      "RPC error code: -32602 , RPC error message: Invalid params")
  }


  /**
   * Test Get Status
   */

  test("Get Node Status") {
    val nodeSatatus = client.getStatus()
    //assert(nodeSatatus.peers.size == client.getPeers().size)
    info("assert node pub key is : 01cd807fb41345d8dD5A61da7991e1468173acbEE53920E4DFe0D28Cb8825AC664")
    assert(nodeSatatus.our_public_signing_key.formatAsHexAccount.get.toLowerCase == "01cd807fb41345d8dD5A61da7991e1468173acbEE53920E4DFe0D28Cb8825AC664".toLowerCase)
    info("assert network is : casper-test ")
    assert(nodeSatatus.chainspec_name == "casper-test")
  }

  /**
   * Test getBlockTransfers
   */

  test("getBlockTransfers ") {
    val transfers = client.getBlockTransfers("a623841478381D78C769636582305ef724f561d7314B4daED19A3EA6373Dd778")
    assert(!transfers.isEmpty)
    info("assert this block contains two transfert")
    assert(transfers.size == 2)
    info("assert first transfert deploy hash is = 277AEF49321B3b19B0EDd732Cd5CFf4F2E76c1Df0260356367711aD81f4bC8FC")
    assert(transfers(0).deploy_hash.toLowerCase == "277AEF49321B3b19B0EDd732Cd5CFf4F2E76c1Df0260356367711aD81f4bC8FC".toLowerCase)
    info("assert amount of first transfert is = 1000000000000 motes")
    assert(transfers(0).amount == 1000000000000L)
    //add tests with  from and to  clpubkeys
  }

  /**
   * Test getAuctionInfo
   */

  test("getAuctionInfo with a block hash , retieves current auction state") {
    val auctionInfo = client.getAuctionInfo("3a4EfA0AA223bF713bEDB5fa8D6dEc29a008C923aec0ACB02A3e4e449b9E01a8")
    info("assert  state_root_hash = current state root hash")
    assert(auctionInfo.state_root_hash == client.getStateRootHash(""))
  }


  /**
   * Test getAuctionInfo with empty block hash
   */

  test("getAuctionInfo with empty block hash, retieves current auction state") {
    val auctionInfo = client.getAuctionInfo("")
    info("assert  state_root_hash = current state root hash")
    assert(auctionInfo.state_root_hash == client.getStateRootHash(""))
    // info("assert  bids size = 1524")
    // assert(auctionInfo.bids.size == 1524)
  }


  /**
   * Test getEraInfoBySwitchBlock with a switch block
   */

  test("getEraInfoBySwitchBlock : 2974-->2975 : 1e46B4c173dB70fDE0E867FF679ACa24e1c5Bea3C4333af94e53B4E3BC548B6B") {
    val erasummury = client.getEraInfoBySwitchBlock("1e46B4c173dB70fDE0E867FF679ACa24e1c5Bea3C4333af94e53B4E3BC548B6B")
    info("assert  era = 2974")
    assert(erasummury.era_id == 2974)
    info("assert  state root hash  = c1A62d5DeB74d3fEAfeCd1EEa526941edd0264895EB8E516474108D4EA4D7D21")
    assert(erasummury.state_root_hash.toLowerCase == "c1A62d5DeB74d3fEAfeCd1EEa526941edd0264895EB8E516474108D4EA4D7D21".toLowerCase)

  }


  /**
   * Test getEraInfoBySwitchBlock with a non switch block
   */

  test("getEraInfoBySwitchBlock non switch bloc : a2C85FD13bba200E47c2d7c74E87F0f0c96aDcEE227877168a639Fa67d046933 , throws RPCException") {

    val caught: RPCException = intercept[RPCException] {
      client.getEraInfoBySwitchBlock("a2C85FD13bba200E47c2d7c74E87F0f0c96aDcEE227877168a639Fa67d046933")
    }
    assert(caught.getMessage == "No result was returned when invoking RPC method: CHAIN_GET_ERA_INFO_BY_SWITCH_BLOCK with params: ArraySeq(Map(Hash -> a2C85FD13bba200E47c2d7c74E87F0f0c96aDcEE227877168a639Fa67d046933)). RPC error code: 0 , RPC error message: No result returned by the RPC Call")
  }


  /**
   * Test getEraInfoBySwitchBlock with an empty  switch block parameter
   */

  test("getEraInfoBySwitchBlock with empty block param, throws RPCException ") {
    val caught: RPCException = intercept[RPCException] {
      client.getEraInfoBySwitchBlock("")
    }
    assert(caught.getMessage == "No result was returned when invoking RPC method: CHAIN_GET_ERA_INFO_BY_SWITCH_BLOCK with params: ArraySeq(Map(Hash -> )). RPC error code: 0 , RPC error message: No result returned by the RPC Call")

  }

  /**
   * Test getStateItem => contract
   */

  test("getStateItem   retrieving Contract") {
    val storedValue = client.getStateItem("30cE5146268305AeeFdCC05a5f7bE7aa6dAF187937Eed9BB55Af90e1D49B7956", "hash-4dd10a0b2a7672e8ec964144634ddabb91504fe50b8461bac23584423318887d", Seq.empty)
    info("assert contract is not null ")
    assert(storedValue.Contract != null)
    info("assert contract_package_hash = contract-wasm-8D1c5ab95baaa77c302232a00FE1C6cF6975ffD6430a9ECd87EC5266280BB18D")
    assert(storedValue.Contract.contract_wasm_hash.toLowerCase == "contract-wasm-8D1c5ab95baaa77c302232a00FE1C6cF6975ffD6430a9ECd87EC5266280BB18D".toLowerCase)
    info("assert first enry point is an allowance ")
    assert(storedValue.Contract.entry_points(0).name == "allowance")
    info("assert first arg of the first entry point is of type CLType = Key  ")
    assert(storedValue.Contract.entry_points(0).args(0).cl_type.toString == "Key")

  }

  /**
   * Test getStateItem => Account
   */
  test("getStateItem   retrieving Account") {
    val storedValue = client.getStateItem("30cE5146268305AeeFdCC05a5f7bE7aa6dAF187937Eed9BB55Af90e1D49B7956", "account-hash-46dE97966cfc2F00C326e654baD000AB7a5E26bEBc316EF4D74715335cF32A88", Seq.empty)
    info("assert contract is  null ")
    assert(storedValue.Contract == null)
    info("assert account is not null ")
    assert(storedValue.Account != null)
    info("assert main_purse : uref-9cC68775d07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d067C4D0363-007")
    assert(storedValue.Account.main_purse.format.toLowerCase == "uref-9cC68775d07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d067C4D0363-007".toLowerCase)
  }

  /**
   * Test getStateItem => CLValue
   */

  test("getStateItem   retrieving CLValue") {
    //TODO
  }

  /**
   * Test getStateItem with with wrong parameters
   */

  test("getStateItem with wrong parameters , throws RPCException ") {
    val caught: RPCException = intercept[RPCException] {
      client.getStateItem("808ad642fefe6a0d3cadfb151a39aecb37183121ae20565ab32f5c04db20513e", "hash-1c1545ab3bdbe0df3823a53c8160fc1960847cd3008376701f73e5e3ff13bbc9", Seq.empty)
    }
    assert(caught.getMessage.toLowerCase == "An error occured when invoking RPC method: state_get_item with params: ArraySeq(808ad642fefe6a0d3cadfb151a39aecb37183121ae20565ab32f5c04db20513e, hash-1c1545ab3bdbe0df3823a53c8160fc1960847cd3008376701f73e5e3ff13bbc9, List()). RPC error code: -32003 , RPC error message: state query failed: ValueNotFound(\"Failed to find base key at path: Key::Hash(1c1545AB3bdbe0DF3823A53C8160fc1960847cD3008376701f73E5E3fF13BbC9)\")".toLowerCase)

  }

  /**
   * Test getStateItem with with wrong parameters
   */

  test("getStateItem with empty parameters , throws RPCException ") {
    val caught: RPCException = intercept[RPCException] {
      client.getStateItem("", "", Seq.empty)
    }
    assert(caught.getMessage == "An error occured when invoking RPC method: state_get_item with params: ArraySeq(, , List()). RPC error code: -32602 , RPC error message: Invalid params")

  }

  /**
   * Test getBalance with existing account
   */
  test("getBalance   with existing account") {
    val balance = client.getBalance("30cE5146268305AeeFdCC05a5f7bE7aa6dAF187937Eed9BB55Af90e1D49B7956", URef("uref-9cC68775d07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d067C4D0363-007"))
    info("Assert balance = 869077209920 motes ")
    assert(balance == 869077209920L)
  }

  /**
   * Test getBalance with non hex key account
   */
  test("getBalance  with non hex key account , throws IllegalArgumentException") {

    val caught: IllegalArgumentException = intercept[IllegalArgumentException] {
      client.getBalance("30cE5146268305AeeFdCC05a5f7bE7aa6dAF187937Eed9BB55Af90e1D49B7956", URef("uref-9cC6877ft07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d067C4D0363-007"))

    }
    assert(caught.getMessage == "Unable to decode: 9cC6877ft07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d067C4D0363")
  }

  /**
   * Test getBalance with non uref key account
   */
  test("getBalance  with non uref key account, throws IllegalArgumentException") {

    val caught: IllegalArgumentException = intercept[IllegalArgumentException] {
      client.getBalance("30cE5146268305AeeFdCC05a5f7bE7aa6dAF187937Eed9BB55Af90e1D49B7956", URef("9cC6877ft07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d067C4D0363-007"))

    }
    assert(caught.getMessage.toLowerCase == "9cC6877ft07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d067C4D0363-007 is not a valid uref".toLowerCase)
  }

  /**
   * Test getDictionaryItem
   */

  test("getDictionaryItem   ") {
    val storedVvalue = client.getDictionaryItem("8180307A39A8583a4a164154C360FB9Ab9B15A5B626295635A62DFc7A82e66a3",
      "a8261377ef9cf8e741dd6858801c71e38c9322e66355586549b75ab24bdd73f2", "uref-F5ea525E6493B41DC3c9b196ab372b6F3f00cA6F1EEf8fe0544e7d044E5480Ba-007")
    info("CLValue is not nul  ")
    assert(storedVvalue.CLValue != null)
    info("cl_Type is String   ")
    assert(storedVvalue.CLValue.cl_infoType.cl_Type == CLType.String)
    info("CLValue parsed =  \"https://caspercommunity.io\" ")
    assert(storedVvalue.CLValue.parsed == "https://caspercommunity.io")
  }


  /**
   * Test PutDeploy
   */

  test("PutDeploy  : Standard Transfer ") {

    //Header
    val header = new DeployHeader(
      CLPublicKey("0168688cd4db3bd37efd84b15dc5a1867465df4c429e17fe22954fea88f5b4e1fe").get,
      System.currentTimeMillis(),
      5400000L,
      1,
      None,
      Seq.empty,
      "casper-test"
    )

    // payment
    val arg0 = new DeployNamedArg("amount", CLValue.U512(2500000000L))
    val payment = new ModuleBytes("".getBytes(), Seq(Seq(arg0)))

    //session
    val arg1 = new DeployNamedArg("amount", CLValue.U512(2500000000L))
    val arg01 = new DeployNamedArg("target", CLValue.PublicKey("017d9aa0b86413d7ff9a9169182c53f0bacaa80d34c211adab007ed4876af17077"))
    val arg02 = new DeployNamedArg("id", CLValue.Option(CLValue.U64(1)))
    val session = new DeployTransfer(Seq(Seq(arg1, arg01, arg02)))

    val keyPair = com.casper.sdk.crypto.KeyPair.loadFromPem(getClass.getResource("/crypto/sign_put_deploys_secret.pem").getPath)
    val deploy = Deploy.createUnsignedDeploy(header, payment, session)
    val signedDeploy = Deploy.signDeploy(deploy, keyPair)
    val hash = client.putDeploy(signedDeploy)
    assert(hash.toString == signedDeploy.hash.get.toString)

  }

}

