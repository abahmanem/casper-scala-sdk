package com.casper.sdk.crypto

import com.casper.sdk.domain.Peer
import com.casper.sdk.rpc.RPCResult
import com.casper.sdk.types.cltypes.{CLPublicKey, KeyAlgorithm}
import com.casper.sdk.util.{HexUtils, JsonConverter}
import org.scalatest.funsuite.AnyFunSuite

import scala.io.Source


class KeyPairTest extends AnyFunSuite {

  test("Test Load keyPair from ed25519 private pem file") {

    val keyPair = KeyPair.loadFromPem(getClass.getResource("/crypto/ed25519/secret.pem").getPath)
    info("assert publickey is not null")
    assert(keyPair.publicKey != null)

    info("assert publickey is of Ed25519PublicKeyParameters  ")
    assert(keyPair.publicKey.getClass.getSimpleName == "Ed25519PublicKeyParameters")

    info("assert private key is not null")
    assert(keyPair.privateKey != null)

    info("assert privateKey is of Ed25519PrivateKeyParameters  ")
    assert(keyPair.privateKey.getClass.getSimpleName == "Ed25519PrivateKeyParameters")

    info("assert CLPublickey key is not null")
    assert(keyPair.cLPublicKey != null)

    info("assert Algorithm is  ED25519")
    assert(keyPair.cLPublicKey.keyAlgorithm == KeyAlgorithm.ED25519)

    info("assert  cLPublicKey hex = 0127a89db4e0806e568a5b0646594bd5d0abe0cf695a63357bd066f412e92bd68e")
    assert(keyPair.cLPublicKey.formatAsHexAccount.get.toLowerCase == "0127a89db4e0806e568a5b0646594bd5d0abe0cf695a63357bd066f412e92bd68e")

  }


  test("Test Load keyPair from secp256k1 private pem file") {
    val keyPair = KeyPair.loadFromPem(getClass.getResource("/crypto/secp256k1/secret.pem").getPath)
    info("assert publickey is not null")
    assert(keyPair.publicKey != null)

    info("assert publickey is of ECPublicKeyParameters  ")
    assert(keyPair.publicKey.getClass.getSimpleName == "ECPublicKeyParameters")

    info("assert private key is not null")
    assert(keyPair.privateKey != null)

    info("assert privateKey is of ECPrivateKeyParameters  ")
    assert(keyPair.privateKey.getClass.getSimpleName == "ECPrivateKeyParameters")

    info("assert CLPublickey key is not null")
    assert(keyPair.cLPublicKey != null)

    info("assert Algorithm is  SECP256K1")
    assert(keyPair.cLPublicKey.keyAlgorithm == KeyAlgorithm.SECP256K1)

    info("assert SECP256K1 key length is  33")
    assert(keyPair.cLPublicKey.bytes.length ==33)

    info("assert  cLPublicKey hex = 0127a89db4e0806e568a5b0646594bd5d0abe0cf695a63357bd066f412e92bd68e")
    assert(keyPair.cLPublicKey.formatAsHexAccount.get.toLowerCase == "0202ba7f1ec7b61e8b79cdd669f0dbf73d40dc08133019f3eba95e43798601cd82ba")
  }


  test("Test Load keyPair from non private pem file") {
    info("Load keyPair from non private pem file Throws IllegalArgumentException")
    val caught: IllegalArgumentException = intercept[IllegalArgumentException] {
      val keyPair = KeyPair.loadFromPem(getClass.getResource("/crypto/ED25519_public_key.pem").getPath)
    }
    assert(caught.getMessage == "this not a private pem file")
  }


  test("Test create ed25519 keyPair ") {

    val keyPair = KeyPair.create(KeyAlgorithm.ED25519)
    info("assert publickey is not null")
    assert(keyPair.publicKey != null)

    info("assert publickey is of Ed25519PublicKeyParameters  ")
    assert(keyPair.publicKey.getClass.getSimpleName == "Ed25519PublicKeyParameters")

    info("assert private key is not null")
    assert(keyPair.privateKey != null)

    info("assert privateKey is of Ed25519PrivateKeyParameters  ")
    assert(keyPair.privateKey.getClass.getSimpleName == "Ed25519PrivateKeyParameters")

    info("assert CLPublickey key is not null")
    assert(keyPair.cLPublicKey != null)

  }

  test("Test create secp256k1 keyPair ") {
    val keyPair = KeyPair.create(KeyAlgorithm.SECP256K1)

    info("assert publickey is not null")
    assert(keyPair.publicKey != null)

    info("assert publickey is of ECPublicKeyParameters  ")
    assert(keyPair.publicKey.getClass.getSimpleName == "ECPublicKeyParameters")

    info("assert private key is not null")
    assert(keyPair.privateKey != null)

    info("assert privateKey is of ECPrivateKeyParameters  ")
    assert(keyPair.privateKey.getClass.getSimpleName == "ECPrivateKeyParameters")

    info("assert CLPublickey key is not null")
    assert(keyPair.cLPublicKey != null)

  }


  test("Test publicKeyToPem from a  ed25519 keyPair ") {
    val keyPair = KeyPair.create(KeyAlgorithm.ED25519)
    assert(keyPair.privateKeyToPem!=null)
  }


  test("Test privateKeyToPem from a ed25519 keyPair ") {
    val keyPair = KeyPair.create(KeyAlgorithm.ED25519)
    assert(keyPair.privateKeyToPem!=null)
  }


  test("Test publicKeyToPem from a secp256k1 keyPair ") {
    val keyPair = KeyPair.create(KeyAlgorithm.SECP256K1)
    assert(keyPair.privateKeyToPem!=null)

  }


  test("Test privateKeyToPem from a secp256k1 keyPair ") {
    val keyPair = KeyPair.create(KeyAlgorithm.SECP256K1)
    assert(keyPair.privateKeyToPem!=null)
  }


  test("Test sign message with ED25519 keyPair ") {
    val msg = "This a test to sign !!".getBytes
    val keyPair = KeyPair.create(KeyAlgorithm.ED25519)
    val b = keyPair.sign(msg)
    info("assert ED25519 signature length is 64  ")
    assert(b.length==64)
    info("assert verifySignature  ED25519 publickey= true  ")
    assert(keyPair.cLPublicKey.verifySignature(msg, b))

  }

  test("Test sign message with SECP256K1 keyPair ") {
    val msg = "This a test to sign !!".getBytes
    val keyPair = KeyPair.create(KeyAlgorithm.SECP256K1)
    val b = keyPair.sign(msg)
    info("assert verifySignature with SECP256K1 publickey = true  ")
    assert(keyPair.cLPublicKey.verifySignature(msg, b))
  }

  test("Test verifiy signature with ed25519 keyPair gives false ") {
    val msg = "This a test to sign !!".getBytes
    val msg1 = "This a test to sign !!!".getBytes
    val keyPair = KeyPair.create(KeyAlgorithm.ED25519)
    val b = keyPair.sign(msg)
    info("assert verifySignature on a différent message with ed25519 publickey = false  ")
    assert(!keyPair.cLPublicKey.verifySignature(msg1, b))

  }

  test("Test verifiy signature with SECP256K1 keyPair gives false ") {
    val msg = "This a test to sign !!".getBytes
    val msg1 = "This a test to sign !!!".getBytes
    val keyPair = KeyPair.create(KeyAlgorithm.SECP256K1)
    val b = keyPair.sign(msg)
    info("assert verifySignature on a différent message with SECP256K1 publickey = false  ")
    assert(!keyPair.cLPublicKey.verifySignature(msg1, b))

  }


}
