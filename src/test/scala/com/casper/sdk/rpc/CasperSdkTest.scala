package com.casper.sdk.rpc

import com.casper.sdk.CasperSdk
import com.casper.sdk.rpc.exceptions.RPCIOException
import com.casper.sdk.util.implicits.*
import org.scalatest.funsuite.AnyFunSuite

/**
 *
 */

class CasperSdkTestSuite extends AnyFunSuite {

  test("RPC Call to a non existing node Throws an RPCIOException") {
    assertThrows[RPCIOException] { // Result type: Assertion
      val cspr = new CasperSdk("http://1.2.3.4:7777/rpc")
      val peers = cspr.info_get_peers()
    }
  }

  val client = new CasperSdk("http://65.21.227.180:7777/rpc")

  test("Testnet Network Peers list is not empty") {
    val peers = client.info_get_peers()
    assert(!peers.isEmpty)
  }

  test("Get state root hash with no blockHash paramter, state root hash is not empty and is 64 characters long") {
    val stateRootHash = client.state_root_hash("")
    assert(!stateRootHash.isEmpty)
    assert(stateRootHash.length == 64)
  }

  test("Get state root hash with a blockHash parameter ,state root hash is not empty and is 64 character  long") {
    val stateRootHash = client.state_root_hash("7ed13ec0f9b1955069fe96e6441fff700e6baed36e8cb99400da0449fae6c95c")
    assert(!stateRootHash.isEmpty)
    assert(stateRootHash.length == 64)
  }

}
