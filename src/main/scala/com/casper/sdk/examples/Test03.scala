package com.casper.sdk.examples

import cats.Id
import com.casper.sdk._
import com.casper.sdk.core._
import com.casper.sdk.result._
import com.casper.sdk.core.implicits.idInstance

object Test03 extends App {

  val transport = new HttpTransport("http://65.21.202.120:7777/rpc",new JsonConverterByClass,100000,100000)
  val cspr = new SDK(transport)


 //val casper = new SDK[Id](transport)

  // println(cspr.get_info_get_peers(Seq("f")))
  // println(cspr.get_chain_get_state_root_hash(Seq("f")))

val params : List[String] = List("e28d3f99bde0dce18ecf5e43f8bcf01545e813ff471925c9ad8c1a85bc74576d","uref-3b4b07f9b59db6af01f74f13deef8b00f62aa6a3905c0355d6467693bf09466f-007");
  println(cspr.state_get_balance(params));



}
