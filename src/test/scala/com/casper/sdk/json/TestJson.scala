package com.casper.sdk.json

import com.casper.sdk.util.JsonConverter

object TestJson extends App {


  case class A(x:Int, y:Option[String])


  val a = new A(1,Some("test"))

  println(JsonConverter.toJson_01(a).get)


  val json = JsonConverter.toJson_01(a).get


  println(JsonConverter.fromJson_01[A](json).get)



  val test01 = Test01(Some("abcd"))

  println(JsonConverter.toJson_01(test01).get)


  val json01 = JsonConverter.toJson_01(test01).get

  println(JsonConverter.fromJson_01[Test01](json01).get)


}
