package com.casper.sdk.crypto

import org.bouncycastle.crypto.AsymmetricCipherKeyPair
import org.bouncycastle.crypto.params.{AsymmetricKeyParameter, DSAKeyParameters, ECKeyParameters, RSAKeyParameters}
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo
import org.bouncycastle.asn1.x509.{AlgorithmIdentifier, SubjectPublicKeyInfo}
import java.security.{KeyFactory, PrivateKey, PublicKey}


import org.bouncycastle.crypto.util.{PrivateKeyFactory, PrivateKeyInfoFactory, PublicKeyFactory, SubjectPublicKeyInfoFactory}

class Conversion {

}
/*

implicit class AsymmetricKeyParameterOps(private val key: AsymmetricKeyParameter) extends AnyVal {
  def algorithm: String = {
    key match {
      case _: ECKeyParameters ⇒
        "ECDSA"

      case _: RSAKeyParameters ⇒
        "RSA"

      case _: DSAKeyParameters ⇒
        "DSA"

      case _ ⇒
        throw new IllegalArgumentException(s"Unknown key algorithm: ${key.getClass}")
    }
  }

  def toSubjectPublicKeyInfo: SubjectPublicKeyInfo = {
    toPublicKey.toSubjectPublicKeyInfo
  }

  def toPrivateKeyInfo: PrivateKeyInfo = {
    toPrivateKey.toPrivateKeyInfo
  }

  def toPrivateKey: PrivateKey = {
    val keyGenerator = KeyFactory.getInstance(this.algorithm, TLSUtils.provider)
    keyGenerator.generatePrivate(new PKCS8EncodedKeySpec(PrivateKeyInfoFactory.createPrivateKeyInfo(key).getEncoded))
  }

  def toPublicKey: PublicKey = {
    val keyGenerator = KeyFactory.getInstance(this.algorithm, TLSUtils.provider)
    keyGenerator.generatePublic(new X509EncodedKeySpec(SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(key).getEncoded))
  }

}

/*

 */
implicit class JavaKeyOps(private val key: java.security.Key) extends AnyVal {
  private def convertPKCS8Key(data: Array[Byte], public: SubjectPublicKeyInfo): AsymmetricCipherKeyPair = {
    new AsymmetricCipherKeyPair(PublicKeyFactory.createKey(public), PrivateKeyFactory.createKey(data))
  }

  def toAsymmetricCipherKeyPair(public: SubjectPublicKeyInfo): AsymmetricCipherKeyPair = key match {
    case privateKey: java.security.PrivateKey ⇒
      convertPKCS8Key(privateKey.getEncoded, public)

    case _ ⇒
      throw new IllegalArgumentException(s"Not supported: ${key.getClass}")
  }

  def toAsymmetricKeyParameter: AsymmetricKeyParameter = key match {
    case privateKey: java.security.PrivateKey ⇒
      PrivateKeyFactory.createKey(key.getEncoded)

    case publicKey: java.security.PublicKey ⇒
      PublicKeyFactory.createKey(key.getEncoded)

    case _ ⇒
      throw new IllegalArgumentException(s"Not supported: ${key.getClass}")
  }

  def toSubjectPublicKeyInfo: SubjectPublicKeyInfo = {
    SubjectPublicKeyInfo.getInstance(key.getEncoded)
  }

  def toPrivateKeyInfo: PrivateKeyInfo = {
    PrivateKeyInfoFactory.createPrivateKeyInfo(toAsymmetricKeyParameter)
  }
}

*/