package com.casper.sdk.json

import com.casper.sdk.util.JsonConverter

object TestJson extends App {


  case class A(x:Int, y:Option[String])


  val a = new A(1,Some("test"))

  println(JsonConverter.toJson_01(a))



}
