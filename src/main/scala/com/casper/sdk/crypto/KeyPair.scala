package com.casper.sdk.crypto

import com.casper.sdk.crypto.util.{Crypto, SECP256K1}
import com.casper.sdk.types.cltypes.{AccountHash, CLPublicKey, KeyAlgorithm}
import com.casper.sdk.util.{ByteUtils, HexUtils}
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters
import org.bouncycastle.crypto.util.PrivateKeyFactory
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.{PEMKeyPair, PEMParser}

import java.io.{FileReader, IOException}
import java.security._
import scala.util.{Success, Try}


/**
 * KeyPair holder
 *
 * @param privateKey private key (java.security.PrivateKey)
 * @param publicKey  CLPublicKey
 */
case class KeyPair(privateKey: PrivateKey, publicKey: Option[CLPublicKey]) {

  /**
   *
   * @return
   */
  def privateToPem: Option[String] = Crypto.toPem(privateKey)

  /**
   *
   * @return
   */
  def publicToPem: Option[String] = Try {
    Crypto.toPem(Crypto.fromCLPublicKey(publicKey.get).get)
  }
  match {
    case Success(x) => x
    case _ => None
  }

  /**
   * sign a message
   *
   * @param msg : message to sign
   * @return byte array
   */
  def sign(msg: Array[Byte]): Array[Byte] = {
    require(msg != null)
    Try {
      publicKey.get.keyAlgorithm match {
        case KeyAlgorithm.SECP256K1 => SECP256K1.sign(msg, this)
        case KeyAlgorithm.ED25519 => {
          val sig = Signature.getInstance(privateKey.getAlgorithm, BouncyCastleProvider.PROVIDER_NAME)
          sig.initSign(privateKey)
          sig.update(msg)
          sig.sign()
        }
      }
    }
    match {
      case Success(x) => x
      case _ => null
    }
  }
}

/**
 * companion object
 */

object KeyPair {

  /**
   * load Keypair from private key pem file
   *
   * @param path : path to private pem file
   * @return KeyPair instance
   */
  def loadFromPem(path: String): Option[KeyPair] = {
    require(path != null)
    Option(new PEMParser(new FileReader(path)).readObject()) match {
      case Some(obj) => obj match {
        case pvkeyInfo: PrivateKeyInfo => Try {
          val privKey = Crypto.converter.getPrivateKey(pvkeyInfo)
          val privkeyparam = PrivateKeyFactory.createKey(pvkeyInfo)
          val pubkeyparam = privkeyparam.asInstanceOf[Ed25519PrivateKeyParameters].generatePublicKey()
          new KeyPair(privKey, Some(new CLPublicKey(pubkeyparam.getEncoded, KeyAlgorithm.ED25519)))
        }
        match {
          case Success(x) => Some(x)
          case _ => None
        }
        case pemKeyPair: org.bouncycastle.openssl.PEMKeyPair => Try {
          val keypair = Crypto.converter.getKeyPair(pemKeyPair.asInstanceOf[PEMKeyPair])
          new KeyPair(keypair.getPrivate, Crypto.toCLPublicKey(keypair.getPublic))
        }
        match {
          case Success(x) => Some(x)
          case _ => None
        }
        case _ => None
      }
      case None => None
    }
  }

  /**
   * create a new KeyPair from a given algoritm
   * @param algo
   * @return  Option[KeyPair]
   */

  def create(algo: KeyAlgorithm): Option[KeyPair] = {
    require(algo != null)
    algo match {
      case KeyAlgorithm.ED25519 => Try {
        val keyPair = Crypto.newKeyPair(algo.toString.toLowerCase(), algo.toString.toLowerCase()).get
        new KeyPair(keyPair.getPrivate, Crypto.toCLPublicKey(keyPair.getPublic))
      }
      match {
        case Success(x) => Some(x)
        case _ => None
      }
      case KeyAlgorithm.SECP256K1 => Try {
        val keyPair = Crypto.newKeyPair("ECDSA", "secp256k1").get
        new KeyPair(keyPair.getPrivate, Crypto.toCLPublicKey(keyPair.getPublic))
      }
      match {
        case Success(x) => Some(x)
        case _ => None
      }
    }
  }
}