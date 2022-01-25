package com.casper.sdk.crypto.util

import com.casper.sdk.types.cltypes.{CLPublicKey, KeyAlgorithm}
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters
import org.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey
import org.bouncycastle.jcajce.provider.asymmetric.edec.BCEdDSAPublicKey
import org.bouncycastle.jce.ECNamedCurveTable
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.jce.spec.ECPublicKeySpec
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter

import java.security.spec.ECGenParameterSpec
import java.security._

object Crypto {

  Security.addProvider(new BouncyCastleProvider)
  val converter = new JcaPEMKeyConverter().setProvider("BC")


  /**
   *
   * @param algo
   * @param ecurve
   * @return
   */
  def newKeyPair(algo: String, ellipticCurve: String) = {
    val keyPairGenerator = KeyPairGenerator.getInstance(algo, BouncyCastleProvider.PROVIDER_NAME)
    val ecGenParameterSpec = new ECGenParameterSpec(ellipticCurve)
    keyPairGenerator.initialize(ecGenParameterSpec, new SecureRandom())
    keyPairGenerator.generateKeyPair()
  }

  /**
   *
   * @param publicKey
   * @return
   */
  def toCLPublicKey(publicKey: PublicKey): CLPublicKey = {
    require(publicKey != null)
    publicKey match {
      case bcec: BCECPublicKey => new CLPublicKey(bcec.getQ.getEncoded(true), KeyAlgorithm.SECP256K1)
      case bced: BCEdDSAPublicKey => new CLPublicKey(bced.getPointEncoding, KeyAlgorithm.ED25519)
    }
  }

  /**
   *
   * @param cLpublicKey
   * @return
   */
  def fromCLPublicKey(cLpublicKey: CLPublicKey): PublicKey = {
    require(cLpublicKey != null)
    cLpublicKey.keyAlgorithm match {
      case KeyAlgorithm.ED25519 => {
        val params = Ed25519PublicKeyParameters(cLpublicKey.bytes)
        val info = SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(params)
        converter.getPublicKey(info)

      }
      case KeyAlgorithm.SECP256K1 => {
        val keyFactory = KeyFactory.getInstance("ECDSA", BouncyCastleProvider.PROVIDER_NAME)
        val ecSpec = ECNamedCurveTable.getParameterSpec("secp256k1")
        val point = ecSpec.getCurve().decodePoint(cLpublicKey.bytes)
        val pubSpec = new ECPublicKeySpec(point, ecSpec)
        keyFactory.generatePublic(pubSpec)

      }
    }
  }
}
