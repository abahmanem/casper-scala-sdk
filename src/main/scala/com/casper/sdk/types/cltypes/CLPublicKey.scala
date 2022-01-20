package com.casper.sdk.types.cltypes

import com.casper.sdk.crypto.{Pem, SECP256K1}
import com.casper.sdk.json.deserialize.CLPublicKeyDeserializer
import com.casper.sdk.json.serialize.CLPublicKeySerializer
import com.casper.sdk.types.cltypes.CLPublicKey.dropAlgorithmBytes
import com.casper.sdk.types.cltypes.KeyAlgorithm
import com.casper.sdk.util.{ByteUtils, HexUtils}
import com.fasterxml.jackson.databind.annotation.{JsonDeserialize, JsonSerialize}
import org.bouncycastle.asn1.edec.EdECObjectIdentifiers
import org.bouncycastle.asn1.x509.{AlgorithmIdentifier, SubjectPublicKeyInfo}
import org.bouncycastle.crypto.params.{ECDomainParameters, ECKeyGenerationParameters, ECPublicKeyParameters, Ed25519PublicKeyParameters}
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey
import org.bouncycastle.jcajce.provider.asymmetric.edec.BCEdDSAPublicKey
import org.bouncycastle.jce.ECNamedCurveTable
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.jce.spec.{ECParameterSpec, ECPublicKeySpec}
import org.bouncycastle.openssl.PEMParser
import org.bouncycastle.openssl.jcajce.{JcaPEMKeyConverter, JcaPEMWriter}
import org.bouncycastle.util.io.pem.{PemObject, PemObjectParser}

import java.io._
import java.nio.file.{FileSystems, Files, Paths}
import java.security.{KeyFactory, PublicKey, SecureRandom, Signature}


/**
 * CLPublicKey : Casper system public key
 *
 * @param bytes
 */
@JsonSerialize(`using` = classOf[CLPublicKeySerializer])
@JsonDeserialize(`using` = classOf[CLPublicKeyDeserializer])
class CLPublicKey(
                   val bytes: Array[Byte],
                   val keyAlgorithm: KeyAlgorithm
                 ) extends Tag {

  /**
   * format to Hex account , ie : 0106cA7c39cD272DbF21a86EeB3B36B7c26E2e9b94af64292419f7862936bcA2cA, 01 being tag bytes
   *
   * @return
   */
  def formatAsHexAccount: String = HexUtils.toHex(formatAsByteAccount)
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
  def toPemString(): String = {
    keyAlgorithm match {
      case KeyAlgorithm.ED25519 => {
        val pubKeyInfo = new SubjectPublicKeyInfo(new AlgorithmIdentifier(EdECObjectIdentifiers.id_Ed25519), bytes)
        Pem.toPem(pubKeyInfo)
      }
      case KeyAlgorithm.SECP256K1 => {
        val eCParameterSpec = ECNamedCurveTable.getParameterSpec("secp256k1")
        val domParam = new ECDomainParameters(eCParameterSpec.getCurve, eCParameterSpec.getG, eCParameterSpec.getN, eCParameterSpec.getH, eCParameterSpec.getSeed)
        val q = eCParameterSpec.getCurve.decodePoint(bytes)
        val pk: ECPublicKeyParameters = new ECPublicKeyParameters(q, domParam);
        Pem.toPem(pk)
      }
      case null => throw new IllegalArgumentException("algorithm not handled")
    }
  }

  /**
   * Verifies a signed message
   *
   * @param msg       the message to sign
   * @param signature signature of the signed message
   * @return true if the signature is valid and false if not
   */
  def verifySignature(msg: Array[Byte], signature: Array[Byte]): Boolean = {

    keyAlgorithm match {
      case KeyAlgorithm.ED25519 => {
        import org.bouncycastle.math.ec.rfc8032.Ed25519
        Ed25519.verify(signature, 0, bytes, 0, msg, 0, msg.length)
      }
      case KeyAlgorithm.SECP256K1 => SECP256K1.verify(msg, signature, bytes)
    }
  }

  /**
   * tag =1
   *
   * @return
   */
  def tag = 1
}

/**
 * Companion object
 */
object CLPublicKey {

  /**
   *
   * @param uref
   * @return CLPublicKey
   */
  def apply(hex: String): CLPublicKey = new CLPublicKey(dropAlgorithmBytes(HexUtils.fromHex(hex)), KeyAlgorithm.fromId(hex.charAt(1)))


  /**
   * remove algorithm tag bytes
   *
   * @param key
   * @return
   */
  def dropAlgorithmBytes(key: Array[Byte]): Array[Byte] = {
    key.drop(1)
  }

  /**
   * Extract account hash from publicKey
   *
   * @param publicKey
   * @return
   */
  def accountHash(publicKey: CLPublicKey): AccountHash = {
    null
  }

  /**
   * load CLPublic key from pem file
   *
   * @param path
   * @return
   */
  def fromPemFile(path: String): CLPublicKey = {

    val converter = new JcaPEMKeyConverter().setProvider(new BouncyCastleProvider());
    Option(new PEMParser(new FileReader(path)).readObject()) match {
      case Some(obj) => obj match {
        case pubkeyInfo: SubjectPublicKeyInfo => {
          val pkey = converter.getPublicKey(pubkeyInfo)
          pkey match {
            case ed: BCEdDSAPublicKey => {
              new CLPublicKey(ed.getPointEncoding(), KeyAlgorithm.ED25519)
            }
            case ec: BCECPublicKey => {
              new CLPublicKey(ec.getQ().getEncoded(true), KeyAlgorithm.SECP256K1)
            }
          }
        }
        case _ => throw new IllegalArgumentException("this not a public pem file")
      }
      case None => throw new IOException("could not read public  key File")
    }
  }
}
