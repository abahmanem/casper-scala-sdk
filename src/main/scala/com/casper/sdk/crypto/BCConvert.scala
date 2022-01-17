package com.casper.sdk.crypto

import org.bouncycastle.crypto.AsymmetricCipherKeyPair
import org.bouncycastle.crypto.params.{AsymmetricKeyParameter, DSAKeyParameters, ECKeyParameters, RSAKeyParameters}
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo
import org.bouncycastle.asn1.x509.{AlgorithmIdentifier, SubjectPublicKeyInfo}

import java.security.{KeyFactory, PrivateKey, PublicKey, Security}
import org.bouncycastle.crypto.util.{PrivateKeyFactory, PrivateKeyInfoFactory, PublicKeyFactory, SubjectPublicKeyInfoFactory}
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter

/**
 * Conversions between BouncyCastle classes and JCA
 */
object BCConvert {

  Security.addProvider(new BouncyCastleProvider())
  val converter = new JcaPEMKeyConverter().setProvider("BC")

  /**
   * get a SubjectPublicKeyInfo from AsymmetricKeyParameter
   *
   * @param key
   * @return SubjectPublicKeyInfo
   */
  def toSubjectPublicKeyInfo(key: AsymmetricKeyParameter): SubjectPublicKeyInfo = {
    SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(key)
  }

  /**
   * get PrivateKeyInfo from  AsymmetricKeyParameter
   *
   * @param key
   * @return PrivateKeyInfo
   */
  def toPrivateKeyInfo(key: AsymmetricKeyParameter): PrivateKeyInfo = {

    println(key.getClass)
    PrivateKeyInfoFactory.createPrivateKeyInfo(key)
  }

  /**
   * converts java.security.Key to AsymmetricKeyParameter
   *
   * @param key
   * @return
   */

  def toAsymmetricKeyParameter(key: java.security.Key): AsymmetricKeyParameter = key match {
    case privateKey: java.security.PrivateKey ⇒
      PrivateKeyFactory.createKey(key.getEncoded)

    case publicKey: java.security.PublicKey ⇒
      PublicKeyFactory.createKey(key.getEncoded)

    case _ ⇒
      throw new IllegalArgumentException(s"Not supported: ${key.getClass}")
  }


  /**
   * private AsymmetricKeyParameter to java.security.PrivateKey
   *
   * @param key
   * @return
   */
  def getPrivateKey(key: AsymmetricKeyParameter): java.security.PrivateKey = {
    converter.getPrivateKey(toPrivateKeyInfo(key))
  }

  /**
   * PrivateKeyInfo to java.security.PrivateKey
   *
   * @param key
   * @return
   */
  def getPrivateKey(keyInfo: PrivateKeyInfo): java.security.PrivateKey = {
    converter.getPrivateKey(keyInfo)
  }
}
