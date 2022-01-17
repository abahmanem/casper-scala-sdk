package com.casper.sdk.crypto

import com.casper.sdk.types.cltypes.{CLPublicKey, KeyAlgorithm}


object Test extends App {

  CLPublicKey.fromPemFile( getClass.getResource("/crypto/ed25519/secret.pem").getPath)

}
