package com.casper.sdk.examples

import cats.Id
import com.casper.sdk.core.JsonConverterByClass
import com.casper.sdk.core.HttpTransport
import com.casper.sdk.SDK
import com.casper.sdk.core.implicits.idInstance
object Test01 extends App {

  val transport = new HttpTransport("http://65.21.202.120:7777/rpc",new JsonConverterByClass,100000,100000)
  val cspr = new SDK(transport)


  println(cspr.get_info_get_peers(Seq("f")))



}
