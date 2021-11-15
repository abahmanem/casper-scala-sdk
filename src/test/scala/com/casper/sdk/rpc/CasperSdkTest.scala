package com.casper.sdk.rpc

import com.casper.sdk.CasperSdk
import com.casper.sdk.util.implicits.*
import org.scalatest.funsuite.AnyFunSuite

class CasperSdkTestSuite extends AnyFunSuite {


  test("Connection failed") {
    assertThrows[java.lang.Exception] { // Result type: Assertion
      val cspr = new CasperSdk("http://1.2.3.4:7777/rpc")
      val peers = cspr.get_info_get_peers()
    }
  }

  val client = new CasperSdk("http://65.21.202.120:7777/rpc")

  test("peers list not empty") {
    info("Runing test on method : get_info_get_peers")
    val peers = client.get_info_get_peers()
    assert(!peers.peers.isEmpty)
  }


  test("state root hash is not empty with 64 caracs") {

    info("Runing test on method : get_chain_get_state_root_hash")
    val stateRootHash = client.get_state_root_hash("")
    assert(!stateRootHash.state_root_hash.isEmpty)
    assert(stateRootHash.state_root_hash.length == 64)
  }


}
