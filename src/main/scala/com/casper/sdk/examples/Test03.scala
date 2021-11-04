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

  println(cspr.get_info_get_peers(Seq("f")))
  println(cspr.get_chain_get_state_root_hash(Seq("f")))

  // println(cspr.get_info_get_deploy(Seq("f")))



}
