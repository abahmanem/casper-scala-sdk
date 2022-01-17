package com.casper.sdk.crypto

import com.casper.sdk.types.cltypes.KeyAlgorithm


object Test extends App {

  val msg = "This a test to sign !!".getBytes
  val keyPair = KeyPair.create(KeyAlgorithm.ED25519)
  val b = keyPair.sign(msg)
  println(keyPair.cLPublicKey.verifySignature(msg,b))

}
