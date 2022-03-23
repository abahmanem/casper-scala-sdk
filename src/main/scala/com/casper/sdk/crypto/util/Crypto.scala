package com.casper.sdk.crypto.util

import com.casper.sdk.types.cltypes.{CLPublicKey, KeyAlgorithm}
import com.casper.sdk.util.JsonConverter.mapper
import org.bouncycastle.crypto.AsymmetricCipherKeyPair
import org.bouncycastle.crypto.params.{AsymmetricKeyParameter, Ed25519PublicKeyParameters}
import org.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey
import org.bouncycastle.jcajce.provider.asymmetric.edec.BCEdDSAPublicKey
import org.bouncycastle.jce.ECNamedCurveTable
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.jce.spec.ECPublicKeySpec
import org.bouncycastle.openssl.PEMKeyPair
import org.bouncycastle.openssl.jcajce.{JcaPEMKeyConverter, JcaPEMWriter}

import java.io.{IOException, StringWriter}
import java.security.spec.ECGenParameterSpec
import java.security._
import scala.reflect.ClassTag
import scala.util.{Failure, Success, Try}

object Crypto {

  Security.addProvider(new BouncyCastleProvider)
  val converter = new JcaPEMKeyConverter().setProvider("BC")


  /**
   * create a java.security.KeyPair
   * @param algo : algorithm (ED25519 /SECP265K1)
   * @param ecurve : elliptic Curve
   * @return Option[KeyPair]
   */
  def newKeyPair(algo: String, ellipticCurve: String) : Option[KeyPair]= {
    Try {
      val keyPairGenerator = KeyPairGenerator.getInstance(algo, BouncyCastleProvider.PROVIDER_NAME)
      val ecGenParameterSpec = new ECGenParameterSpec(ellipticCurve)
      keyPairGenerator.initialize(ecGenParameterSpec, new SecureRandom())
      keyPairGenerator.generateKeyPair()
    } match {
      case Success(x) => Some(x)
      case Failure(err) => None
    }
  }

  /**
   * get a CLpublicKey for a java.security.PublicKey
   * @param publicKey : java.security.PublicKey
   * @return  : Option[CLPublicKey]
   */
  def toCLPublicKey(publicKey: PublicKey): Option[CLPublicKey] = {
    require(publicKey != null)
    publicKey match {
      case bcec: BCECPublicKey => Option(new CLPublicKey(bcec.getQ.getEncoded(true), KeyAlgorithm.SECP256K1))
      case bced: BCEdDSAPublicKey => Option(new CLPublicKey(bced.getPointEncoding, KeyAlgorithm.ED25519))
    }
  }

  /**
   * get a java.security.PublicKey from a CLPublicKey
   * @param cLpublicKey
   * @return  java.security.PublicKey
   */
  def fromCLPublicKey(cLpublicKey: CLPublicKey): Option[PublicKey] = {
    require(cLpublicKey != null)
    cLpublicKey.keyAlgorithm match {
      case KeyAlgorithm.ED25519 => {
        val params = Ed25519PublicKeyParameters(cLpublicKey.bytes)
        val info = SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(params)
        Try {
          converter.getPublicKey(info)
        } match {
          case Success(x) => Some(x)
          case Failure(err) => None
        }
      }
      case KeyAlgorithm.SECP256K1 => {
        val keyFactory = KeyFactory.getInstance("ECDSA", BouncyCastleProvider.PROVIDER_NAME)
        val ecSpec = ECNamedCurveTable.getParameterSpec("secp256k1")
        val point = ecSpec.getCurve().decodePoint(cLpublicKey.bytes)
        val pubSpec = new ECPublicKeySpec(point, ecSpec)
        Try {
          keyFactory.generatePublic(pubSpec)
        } match {
          case Success(x) => Some(x)
          case Failure(err) =>  None
        }
      }
    }
  }

  /**
   * Writes key objects to pem strings
   *
   * @param data : data to write into pem String
   * @throws IOException
   * @return Pem String
   */
  @throws[IOException]
  def toPem(data: AnyRef): Option[String] = {
    val stringWriter = new StringWriter(4096)
    val writer = new JcaPEMWriter(stringWriter)

    Try {
      data match {
        case keyPair: AsymmetricCipherKeyPair => writer.writeObject(new PEMKeyPair(BCConvert.toSubjectPublicKeyInfo(keyPair.getPublic), BCConvert.toPrivateKeyInfo(keyPair.getPrivate).get))
        case key: AsymmetricKeyParameter if key.isPrivate => writer.writeObject(BCConvert.toPrivateKeyInfo(key).get)
        case key: AsymmetricKeyParameter if !key.isPrivate => writer.writeObject(BCConvert.toSubjectPublicKeyInfo(key))
        case _ => writer.writeObject(data)
      }
      writer.flush()
      stringWriter.toString
    }
    match {
      case Success(x) => Some(x)
      case _ => None
    }
  }
}