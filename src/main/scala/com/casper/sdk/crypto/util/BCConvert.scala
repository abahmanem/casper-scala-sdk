package com.casper.sdk.crypto.util

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
import org.bouncycastle.crypto.params.AsymmetricKeyParameter
import org.bouncycastle.crypto.util.{PrivateKeyFactory, PrivateKeyInfoFactory, PublicKeyFactory, SubjectPublicKeyInfoFactory}
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter

import java.security.Security

/**
 * Conversions between BouncyCastle classes and JCA
 */
object BCConvert {

  Security.addProvider(new BouncyCastleProvider())
  val converter = new JcaPEMKeyConverter().setProvider("BC")

  /**
   * get a SubjectPublicKeyInfo from AsymmetricKeyParameter
   *
   * @param key :AsymmetricKeyParameter
   * @return SubjectPublicKeyInfo
   */
  def toSubjectPublicKeyInfo(key: AsymmetricKeyParameter): SubjectPublicKeyInfo = SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(key)


  /**
   * get PrivateKeyInfo from  AsymmetricKeyParameter
   *
   * @param key : AsymmetricKeyParameter
   * @return PrivateKeyInfo
   */
  def toPrivateKeyInfo(key: AsymmetricKeyParameter): PrivateKeyInfo = PrivateKeyInfoFactory.createPrivateKeyInfo(key)

  /**
   * converts java.security.Key to AsymmetricKeyParameter
   *
   * @param key : java.security.Key
   * @return AsymmetricKeyParameter
   */

  def toAsymmetricKeyParameter(key: java.security.Key): AsymmetricKeyParameter = key match {
    case privateKey: java.security.PrivateKey => PrivateKeyFactory.createKey(key.getEncoded)

    case publicKey: java.security.PublicKey => PublicKeyFactory.createKey(key.getEncoded)

    case _ => throw new IllegalArgumentException(s"Not supported: ${key.getClass}")
  }


  /**
   * private AsymmetricKeyParameter to java.security.PrivateKey
   *
   * @param key : AsymmetricKeyParameter
   * @return java.security.PrivateKey
   */
  def getPrivateKey(key: AsymmetricKeyParameter): java.security.PrivateKey = converter.getPrivateKey(toPrivateKeyInfo(key))


  /**
   * PrivateKeyInfo to java.security.PrivateKey
   *
   * @param key : PrivateKeyInfo
   * @return java.security.PrivateKey
   */
  def getPrivateKey(keyInfo: PrivateKeyInfo): java.security.PrivateKey = converter.getPrivateKey(keyInfo)

}
