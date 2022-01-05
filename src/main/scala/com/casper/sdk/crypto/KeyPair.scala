package com.casper.sdk.crypto

import org.bouncycastle.crypto.params.AsymmetricKeyParameter

/**
 * holder for a key pair (a public key + a private key)
 */
case class KeyPair(publicKey: AsymmetricKeyParameter, privateKey: AsymmetricKeyParameter) {
}
