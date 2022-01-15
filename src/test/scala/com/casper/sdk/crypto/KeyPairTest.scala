package com.casper.sdk.crypto

import com.casper.sdk.types.cltypes.{CLPublicKey, KeyAlgorithm}


object KeyPairTest extends  App {

  val kp = KeyPair.create(KeyAlgorithm.ED25519)

kp.publicKeyToPem()
  kp.privateKeyToPem()

 println(CLPublicKey.fromPemFile("/Users/p35862/test01.pem").formatAsHexAccount)


  val p = new CLPublicKey("0203e7d5b66b2fd0f66fb0efcceecb673b3762595b30ae1cac48ae8f09d34c952ee4")
  p.toPemString()

  val str = """-----BEGIN PUBLIC KEY-----
              |MCowBQYDK2VwAyEAf3R7Z70/5jwqc2c53+QBVtYiNHNG5w9o9RwXinXOVTc=
              |-----END PUBLIC KEY-----""".stripMargin



  println(CLPublicKey.fromPemFile("/Users/p35862/test02.pem").formatAsHexAccount)
  println(CLPublicKey.fromPemFile("/Users/p35862/test04.pem").formatAsHexAccount)


  KeyPair.loadFromPem("/Users/p35862/ec101.pem")
}
