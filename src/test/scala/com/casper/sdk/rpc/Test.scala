package com.casper.sdk.rpc

import com.casper.sdk.CasperSdk
import com.casper.sdk.rpc.result.BlockResult
import com.casper.sdk.util.JsonConverter
import com.casper.sdk.util.implicits.*

object Test  extends  App {


  val client = new CasperSdk("http://65.21.202.120:7777/rpc")

  println(client.chain_get_block())
  println(client.get_state_root_hash("").state_root_hash)
  println(client.get_info_get_peers())

}
