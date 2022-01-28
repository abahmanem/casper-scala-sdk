package com.casper.sdk.crypto

import com.casper.sdk.crypto.util.{Crypto, SECP256K1}
import com.casper.sdk.types.cltypes.{CLPublicKey, KeyAlgorithm}
import com.casper.sdk.util.{ByteUtils, HexUtils}
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters
import org.bouncycastle.crypto.util.PrivateKeyFactory
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.{PEMKeyPair, PEMParser}

import java.io.FileReader
import java.io.IOException
import java.security._


/**
 * KeyPait holder
 * @param privateKey private key
 * @param publicKey  CLPublicKey
 */
case class KeyPair(privateKey : PrivateKey, publicKey: CLPublicKey) {

  /**
   *
   * @return
   */
  def privateToPem: String = Crypto.toPem(privateKey)

  /**
   *
   * @return
   */
  def publicToPem: String = Crypto.toPem(Crypto.fromCLPublicKey(publicKey))

  /**
   * sign a message
   *
   * @param msg : message to sign
   * @return byte array
   */
  def sign(msg: Array[Byte]): Array[Byte] = {
    require(msg != null)

    publicKey.keyAlgorithm match {
      case  KeyAlgorithm.SECP256K1 =>   SECP256K1.sign(msg,this)
      case  KeyAlgorithm.ED25519 => {
        val sig = Signature.getInstance(privateKey.getAlgorithm, BouncyCastleProvider.PROVIDER_NAME)
        sig.initSign(privateKey)
        sig.update(msg)
        sig.sign()

      }
    }
  }
}

/**
 * companion object
 */

object KeyPair{

  /**
   * load Keypair from private key pem file
   *
   * @param path : path to private pem file
   * @return KeyPair instance
   */
  def loadFromPem(path: String): KeyPair = {
    require(path != null)
    Option(new PEMParser(new FileReader(path)).readObject()) match {
      case Some(obj) => obj match {
        case pvkeyInfo: PrivateKeyInfo => {
          val privKey = Crypto.converter.getPrivateKey(pvkeyInfo)
          val privkeyparam = PrivateKeyFactory.createKey(pvkeyInfo)
          val pubkeyparam = privkeyparam.asInstanceOf[Ed25519PrivateKeyParameters].generatePublicKey()
          val pair = new KeyPair(privKey, new CLPublicKey(pubkeyparam.getEncoded, KeyAlgorithm.ED25519))
          pair
        }
        case pemKeyPair: org.bouncycastle.openssl.PEMKeyPair => {
          val keypair = Crypto.converter.getKeyPair(pemKeyPair.asInstanceOf[PEMKeyPair])
          val pair = new KeyPair(keypair.getPrivate, Crypto.toCLPublicKey(keypair.getPublic))
          pair
        }
        case _ => throw new IllegalArgumentException("this not a private pem file")
      }
      case None => throw new IOException("could not read private  key File")
    }
  }

  /**
   *
   * @param algo
   * @return
   */

  def create(algo:KeyAlgorithm) : KeyPair =   {
    algo match {
      case KeyAlgorithm.ED25519 => {
       val keyPair=  Crypto.newKeyPair(algo.toString.toLowerCase(),algo.toString.toLowerCase())
        new KeyPair(keyPair.getPrivate,Crypto.toCLPublicKey(keyPair.getPublic))
      }
      case KeyAlgorithm.SECP256K1 => {
        val keyPair=  Crypto.newKeyPair("ECDSA","secp256k1")
        new KeyPair(keyPair.getPrivate,Crypto.toCLPublicKey(keyPair.getPublic))
      }
    }
  }

}