package com.casper.sdk.rpc

import com.casper.sdk.CasperSdk
import com.casper.sdk.rpc.exceptions.RPCIOException
import com.casper.sdk.util.implicits.*
import org.scalatest.funsuite.AnyFunSuite

/**
 *
 */

class CasperSdkTestSuite extends AnyFunSuite {

  test("RPC Call Throws an RPCIOException") {
    assertThrows[RPCIOException] { // Result type: Assertion
      val cspr = new CasperSdk("http://1.2.3.4:7777/rpc")
      val peers = cspr.info_get_peers()
    }
  }

  val client = new CasperSdk("http://65.21.202.120:7777/rpc")

  test("peers list not empty") {
    val peers = client.info_get_peers()
    assert(!peers.isEmpty)
  }

  test("state root hash is not empty with 64 caracs") {
    val stateRootHash = client.state_root_hash("")
    assert(!stateRootHash.isEmpty)
    assert(stateRootHash.length == 64)
  }

  test("state root hash is not empty with 64 caracs and blockHash parameter") {
    val stateRootHash = client.state_root_hash("45555555555555")
    assert(!stateRootHash.isEmpty)
    assert(stateRootHash.length == 64)
  }

}
