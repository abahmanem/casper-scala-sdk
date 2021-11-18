package com.casper.sdk.rpc

import com.casper.sdk.CasperSdk
import com.casper.sdk.rpc.result.BlockResult
import com.casper.sdk.util.JsonConverter
import com.casper.sdk.util.implicits.*

object Test  extends  App {



  val opt = Some("fsfsgd dgdrg oo")

 println( opt.map("data : " + _).getOrElse("") )




  val client = new CasperSdk("http://65.21.202.120:7777/rpc")
  println(JsonConverter.toJson(new String("c6ed416a5932c1b2b7a5ebd63a8441e498b3b2c9e11ab17fad6163d22714c955")))
  println(JsonConverter.toJson(Seq("c6ed416a5932c1b2b7a5ebd63a8441e498b3b2c9e11ab17fad6163d22714c955")))
 println(client.info_get_peers())


/*
 // println(client.chain_get_block_By_Height(31889930))

  println(client.chain_get_block("c6ed416a5932c1b2b7a5ebd63a8441e498b3b2c9e11ab17fad6163d22714c955"))
  println(client.state_root_hash(""))

  println(client.info_get_status())
  println(client.chain_get_block_transfers("c6ed416a5932c1b2b7a5ebd63a8441e498b3b2c9e11ab17fad6163d22714c955"))
*/
  println(client.state_get_auction_info("c6ed416a5932c1b2b7a5ebd63a8441e498b3b2c9e11ab17fad6163d22714c955"))

}

/*

[
{
  "Height": 317300
}

]
*/