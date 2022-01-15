package com.casper.sdk.crypto

import com.casper.sdk.types.cltypes.{CLPublicKey, KeyAlgorithm}
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
import org.bouncycastle.crypto.params.{AsymmetricKeyParameter, ECDomainParameters, ECPublicKeyParameters, Ed25519PrivateKeyParameters, Ed25519PublicKeyParameters}
import org.bouncycastle.crypto.AsymmetricCipherKeyPair
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
import org.bouncycastle.crypto.KeyGenerationParameters
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator
import org.bouncycastle.jce.ECNamedCurveTable
import org.bouncycastle.util.io.pem.PemObject
import org.bouncycastle.crypto.params.ECKeyGenerationParameters
import org.bouncycastle.crypto.generators.ECKeyPairGenerator

import java.nio.file.{FileSystems, Files, Paths}
import org.bouncycastle.openssl.jcajce.JcaPEMWriter

import java.security.{KeyFactory, SecureRandom, Security}
import java.security.spec.ECPublicKeySpec
import java.io._
import org.bouncycastle.crypto.params.ECPrivateKeyParameters
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey
import org.bouncycastle.jcajce.provider.asymmetric.edec.BCEdDSAPublicKey
import org.bouncycastle.openssl.{PEMParser, PEMWriter}
import org.bouncycastle.jcajce.provider.asymmetric.util.{DESUtil, EC5Util}
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter
import org.bouncycastle.openssl.jcajce.JcaPEMWriter
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.jcajce.provider.asymmetric.edec.BCEdDSAPublicKey
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey
import org.bouncycastle.jcajce.provider.asymmetric.edec.BCEdDSAPrivateKey
import java.security.Security

/**
 * holder for a key pair (a public key + a private key)
 */
case class KeyPair(publicKey: AsymmetricKeyParameter, privateKey: AsymmetricKeyParameter) {

  Security.addProvider( new BouncyCastleProvider())
  val converter = new JcaPEMKeyConverter().setProvider("BC")
  var cLPublicKey : CLPublicKey=null
  /**
   * export publicKey to Pem String
   *
   * @return
   */
  def publicKeyToPem(): String = {
    val p = org.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(publicKey)
    val ppp= converter.getPublicKey(p)
    val writer = new StringWriter
    val jcaWriter = new JcaPEMWriter(writer)
     println(ppp.getClass.getName)
    jcaWriter.writeObject(ppp)
    jcaWriter.flush
    jcaWriter.close()
    println(writer.toString)
    writer.toString
  }

  /**
   * export privateKey to Pem String
   *
   * @return
   */
  def privateKeyToPem(): String = {
    val p = org.bouncycastle.crypto.util.PrivateKeyInfoFactory.createPrivateKeyInfo(privateKey)
    val ppp= converter.getPrivateKey(p)
    val writer = new StringWriter
    val jcaWriter = new JcaPEMWriter(writer)
    println(ppp.getClass.getName)
    jcaWriter.writeObject(ppp)
    jcaWriter.flush
    jcaWriter.close()
    println(writer.toString)
    writer.toString
  }

  /**
   * signa message
   *
   * @param msg
   * @return
   */
  def sign(msg: Array[Byte]): Array[Byte] = {
    null
  }
}

/**
 * campanion object
 */
object KeyPair {

  /**
   * load Keypair from private key pem file
   *
   * @param path
   * @return
   */
  def loadFromPem(path: String): KeyPair = {


    import org.bouncycastle.asn1.pkcs.PrivateKeyInfo


    val o = new PEMParser(new FileReader(path)).readObject()

    println("DDDDDDDD"+o.getClass.getName)

    val converter = new JcaPEMKeyConverter().setProvider(new BouncyCastleProvider());
    Option(new PEMParser(new FileReader(path)).readObject()) match {
      case Some(obj) => obj match {
        case pvkeyInfo: PrivateKeyInfo => {
         val pvkey =  converter.getPrivateKey(pvkeyInfo)
         import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters
         val prvparam = new Ed25519PrivateKeyParameters(pvkey.getEncoded, 0)
         val pubkparam =  prvparam.generatePublicKey()
          print("EEEEEEEEEEE"+pvkey.getClass.getName)
          val p = KeyPair(pubkparam ,prvparam)
          println(p.privateKeyToPem())
          p

        }
        case pemKeyPair: org.bouncycastle.openssl.PEMKeyPair => {
          val pvkey =  converter.getPrivateKey(pemKeyPair.getPrivateKeyInfo)
          print("EEEEEEEEEEE"+pvkey.getClass.getName)
          null
        }


        case _ => throw new IllegalArgumentException("this not a private pem file")
      }
      case None => throw new IOException("could not read private  key File")
    }
  }




  /**
   * create a new Keypair
   *
   * @return
   */
  def create(algo: KeyAlgorithm): KeyPair = {

    algo match {
      case KeyAlgorithm.ED25519=> {
        val pairGenerator = new Ed25519KeyPairGenerator()
        pairGenerator.init(new KeyGenerationParameters(new SecureRandom(), 255))
        val keys = pairGenerator.generateKeyPair()
        val publicKey = keys.getPublic.asInstanceOf[Ed25519PublicKeyParameters]
        val pair = new KeyPair(publicKey,keys.getPrivate)
        pair.cLPublicKey = new CLPublicKey(publicKey.getEncoded,algo)
        pair
      }
      case _ => {

        val eCParameterSpec = ECNamedCurveTable.getParameterSpec("secp256k1")
        val domParams = new ECDomainParameters(eCParameterSpec.getCurve, eCParameterSpec.getG, eCParameterSpec.getN, eCParameterSpec.getH, eCParameterSpec.getSeed)
        val keyParams = new ECKeyGenerationParameters(domParams, new SecureRandom())
        val pairGenerator = new ECKeyPairGenerator()
        pairGenerator.init(keyParams)
        val keys =  pairGenerator.generateKeyPair()
        val pubKey = keys.getPublic.asInstanceOf[ECPublicKeyParameters]
        val pair =new KeyPair(keys.getPublic,keys.getPrivate)
        pair.cLPublicKey= new CLPublicKey(pubKey.getQ.getEncoded(true),algo)
        pair
      }
    }
  }
}