package com.casper.sdk.crypto.util

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
import org.bouncycastle.crypto.params.AsymmetricKeyParameter
import org.bouncycastle.crypto.util.{PrivateKeyFactory, PrivateKeyInfoFactory, PublicKeyFactory, SubjectPublicKeyInfoFactory}
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter

import java.security.Security
import scala.util.{Success, Try}
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
  def toPrivateKeyInfo(key: AsymmetricKeyParameter): Option[PrivateKeyInfo] =
    Try {
      PrivateKeyInfoFactory.createPrivateKeyInfo(key)
    }
    match {
      case Success(x) =>Some(x)
      case _ => None
    }
  /**
   * converts java.security.Key to AsymmetricKeyParameter
   *
   * @param key : java.security.Key
   * @return AsymmetricKeyParameter
   */

  def toAsymmetricKeyParameter(key: java.security.Key): Option[AsymmetricKeyParameter] = key match {
    case privateKey: java.security.PrivateKey => Option(PrivateKeyFactory.createKey(key.getEncoded))
    case publicKey: java.security.PublicKey => Option(PublicKeyFactory.createKey(key.getEncoded))
    case _ => None
  }


  /**
   * private AsymmetricKeyParameter to java.security.PrivateKey
   *
   * @param key : AsymmetricKeyParameter
   * @return java.security.PrivateKey
   */
  def getPrivateKey(key: AsymmetricKeyParameter): Option[java.security.PrivateKey] =
    Try {
      converter.getPrivateKey(toPrivateKeyInfo(key).get)
    }
    match {
      case Success(x) =>Some(x)
      case _ => None
    }



  /**
   * PrivateKeyInfo to java.security.PrivateKey
   *
   * @param key : PrivateKeyInfo
   * @return java.security.PrivateKey
   */
  def getPrivateKey(keyInfo: PrivateKeyInfo): Option[java.security.PrivateKey] =
    Try {
      converter.getPrivateKey(keyInfo)
    }
    match {
      case Success(x) =>Some(x)
      case _ => None
    }
}
