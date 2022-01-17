package com.casper.sdk.crypto

import com.casper.sdk.types.cltypes.{CLPublicKey, KeyAlgorithm}
import com.casper.sdk.util.HexUtils

import scala.io.Source


object KeyPairTest extends  App {


/*
  val p0 = new CLPublicKey("01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900c")
  val p = new CLPublicKey("0203e7d5b66b2fd0f66fb0efcceecb673b3762595b30ae1cac48ae8f09d34c952ee4")
 println( p0.toPemString())
 println( p.toPemString())


  val ed25519Pem = Source.fromURL(getClass.getResource("/crypto/ED25519_public_key.pem")) .mkString
  val secp256K1Pem = Source.fromURL(getClass.getResource("/crypto/SECP256K1_public_key.pem")).mkString
  println( ed25519Pem)
  println( p0.toPemString())


  assert( p0.toPemString() == ed25519Pem)
*/
/*
 println(getClass.getResource("/crypto/ED25519_public_key.pem").getPath)

 println(CLPublicKey.fromPemFile("/Users/p35862/p01.pem").formatAsHexAccount)
 println(CLPublicKey.fromPemFile("/Users/p35862/p02.pem").formatAsHexAccount)
*/
 /*
  val str = """-----BEGIN PUBLIC KEY-----
              |MCowBQYDK2VwAyEAf3R7Z70/5jwqc2c53+QBVtYiNHNG5w9o9RwXinXOVTc=
              |-----END PUBLIC KEY-----""".stripMargin



  println(CLPublicKey.fromPemFile("/Users/p35862/test02.pem").formatAsHexAccount)
  println(CLPublicKey.fromPemFile("/Users/p35862/test04.pem").formatAsHexAccount)

*/
 //val k =  KeyPair.loadFromPem("/Users/p35862/ec101.pem")
  val k =  KeyPair.loadFromPem("/Users/p35862/3333.pem")
val bytes = "errrfrt fgdfgfg hhjghjgh ewrwe".getBytes
val sig= k.sign(bytes)
  println(k.publicKeyToPem)
  println(k.privateKeyToPem)
  println(k.cLPublicKey.formatAsHexAccount)

  //k.cLPublicKey.verifySignature(bytes,sig)

  println(k.cLPublicKey.verifySignature(bytes,sig))




}
