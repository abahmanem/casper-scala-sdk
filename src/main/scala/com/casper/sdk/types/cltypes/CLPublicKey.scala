package com.casper.sdk.types.cltypes

import com.casper.sdk.crypto.KeyPair
import com.casper.sdk.crypto.hash.Blake2b256
import com.casper.sdk.crypto.util.{Crypto, SECP256K1}

import com.casper.sdk.types.cltypes.CLPublicKey
import com.casper.sdk.types.cltypes.KeyAlgorithm
import com.casper.sdk.util.{ByteUtils, HexUtils}
import org.bouncycastle.asn1.edec.EdECObjectIdentifiers
import org.bouncycastle.asn1.x509.{AlgorithmIdentifier, SubjectPublicKeyInfo}
import org.bouncycastle.crypto.params.{ECDomainParameters, ECKeyGenerationParameters, ECPublicKeyParameters, Ed25519PublicKeyParameters}
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey
import org.bouncycastle.jcajce.provider.asymmetric.edec.BCEdDSAPublicKey
import org.bouncycastle.jce.ECNamedCurveTable
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.jce.spec.{ECParameterSpec, ECPublicKeySpec}
import org.bouncycastle.math.ec.rfc8032.Ed25519
import org.bouncycastle.openssl.PEMParser
import org.bouncycastle.openssl.jcajce.{JcaPEMKeyConverter, JcaPEMWriter}
import org.bouncycastle.util.io.pem.{PemObject, PemObjectParser}

import java.io._
import java.nio.file.{FileSystems, Files, Paths}
import java.security.{KeyFactory, PublicKey, SecureRandom, Signature}

import scala.util.{Failure, Success, Try}
import io.circe._
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.deriveEncoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * CLPublicKey : Casper system public key
 *
 * @param bytes
 */
case class CLPublicKey(
                        bytes: Array[Byte],
                        keyAlgorithm: KeyAlgorithm
                      ) extends Tag {

  /**
   * format to Hex account , ie : 0106cA7c39cD272DbF21a86EeB3B36B7c26E2e9b94af64292419f7862936bcA2cA, 01 being tag bytes
   *
   * @return
   */
  def formatAsHexAccount: Option[String] = Try(HexUtils.toHex(formatAsByteAccount).getOrElse("")).toOption

  /**
   * format to Byte array with algorithm
   *
   * @return
   */
  def formatAsByteAccount: Array[Byte] = ByteUtils.join(Array.fill(1)(keyAlgorithm.bits.toByte), bytes)

  /**
   * Write public key to pem file
   *
   * @param path
   */
  def toPemString(): Option[String] = {
    keyAlgorithm match {
      case KeyAlgorithm.ED25519 => {
        val pubKeyInfo = new SubjectPublicKeyInfo(new AlgorithmIdentifier(EdECObjectIdentifiers.id_Ed25519), bytes)
        Crypto.toPem(pubKeyInfo)
      }
      case KeyAlgorithm.SECP256K1 => {
        val eCParameterSpec = ECNamedCurveTable.getParameterSpec("secp256k1")
        val domParam = new ECDomainParameters(eCParameterSpec.getCurve, eCParameterSpec.getG, eCParameterSpec.getN, eCParameterSpec.getH, eCParameterSpec.getSeed)
        val q = eCParameterSpec.getCurve.decodePoint(bytes)
        val pk: ECPublicKeyParameters = new ECPublicKeyParameters(q, domParam);
        Crypto.toPem(pk)
      }
      case null => None
    }
  }

  /**
   * Verifies a signed message
   *
   * @param msg       the message to sign
   * @param signature signature of the signed message
   * @return true if the signature is valid and false if not
   */
  def verifySignature(msg: Array[Byte], signature: Array[Byte]): Either[Throwable, Boolean] = keyAlgorithm match {
    case KeyAlgorithm.ED25519 => Try(Ed25519.verify(signature, 0, bytes, 0, msg, 0, msg.length)).toEither
    case KeyAlgorithm.SECP256K1 => SECP256K1.verify(msg, signature, bytes)
  }


  /**
   * tag =1
   *
   * @return
   */
  override def tag = 1

  /**
   * hashCode
   *
   * @return Int
   */
  override def hashCode(): Int = 31 * java.util.Objects.hash(keyAlgorithm) + java.util.Arrays.hashCode(bytes)

  /**
   * equals
   *
   * @param obj
   * @return Boolean
   */
  override def equals(obj: scala.Any): Boolean = obj match {
    case pubKey: CLPublicKey => pubKey.keyAlgorithm == keyAlgorithm && java.util.Arrays.equals(bytes, pubKey.bytes)
    case _ => false
  }

  /**
   * toString
   *
   * @return String
   */
  // override def toString: String = formatAsHexAccount.getOrElse("")
}

/**
 * Companion object
 */
object CLPublicKey {

  implicit val decoder: Decoder[Option[CLPublicKey]] = Decoder.decodeString.emapTry {
    str => Try(CLPublicKey(str))
  }
  implicit val encoder: Encoder[CLPublicKey] = (pubkey: CLPublicKey) => Encoder.encodeString(pubkey.formatAsHexAccount.getOrElse(""))

  /**
   *
   * @param hex
   * @return CLPublicKey
   */
  def apply(hex: String): Option[CLPublicKey] =Try(new CLPublicKey(dropAlgorithmBytes(HexUtils.fromHex(hex).get), KeyAlgorithm.fromId(hex.charAt(1).asDigit).getOrElse(KeyAlgorithm.ED25519)))
        .toOption
 //TODO : implement CEP57Checksum



  /**
   * remove algorithm tag bytes
   *
   * @param key
   * @return
   */
  def dropAlgorithmBytes(key: Array[Byte]): Array[Byte] = key.drop(1)


  /**
   * Extract account hash from publicKey
   *
   * @param publicKey
   * @return
   */
  def accountHash(publicKey: CLPublicKey): Option[String] = Try(
    KeyType.Account.prefix + HexUtils.toHex(Blake2b256.CLPublicKeyToAccountHash(publicKey).get)
  ).toOption


  /**
   * load CLPublic key from pem file
   *
   * @param path
   * @return
   */
  def fromPemFile(path: String): Option[CLPublicKey] = Try {
    val converter = new JcaPEMKeyConverter().setProvider(new BouncyCastleProvider());
    Option(new PEMParser(new FileReader(path)).readObject()) match {
      case Some(obj) => obj match {
        case pubkeyInfo: SubjectPublicKeyInfo => converter.getPublicKey(pubkeyInfo) match {
          case ed: BCEdDSAPublicKey => new CLPublicKey(ed.getPointEncoding(), KeyAlgorithm.ED25519)
          case ec: BCECPublicKey => new CLPublicKey(ec.getQ().getEncoded(true), KeyAlgorithm.SECP256K1)
        }
      }
      case _ => null
    }
  }.toOption
}