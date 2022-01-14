package com.casper.sdk.crypto

import com.casper.sdk.types.cltypes.{CLPublicKey, KeyAlgorithm}
import org.bouncycastle.crypto.params.{AsymmetricKeyParameter, ECDomainParameters, ECPublicKeyParameters, Ed25519PrivateKeyParameters, Ed25519PublicKeyParameters}
import org.bouncycastle.crypto.AsymmetricCipherKeyPair
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
import org.bouncycastle.crypto.KeyGenerationParameters
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator
import org.bouncycastle.jce.ECNamedCurveTable
import org.bouncycastle.util.io.pem.PemObject
import org.bouncycastle.crypto.params.ECKeyGenerationParameters
import  org.bouncycastle.crypto.generators.ECKeyPairGenerator
import java.nio.file.{Files, Paths, FileSystems}
import org.bouncycastle.openssl.jcajce.JcaPEMWriter
import java.security.SecureRandom
import java.io._
import org.bouncycastle.crypto.params.ECPrivateKeyParameters
import org.bouncycastle.openssl.PEMWriter
/**
 * holder for a key pair (a public key + a private key)
 */
case class KeyPair(publicKey: AsymmetricKeyParameter, privateKey: AsymmetricKeyParameter) {

  var cLPublicKey : CLPublicKey=null
  /**
   * export publicKey to Pem String
   *
   * @return
   */
  def publicKeyToPem(): String = {
    val writer = new StringWriter
    val pemWriter = new JcaPEMWriter(writer)
    pemWriter.writeObject(publicKey)
    pemWriter.flush
    pemWriter.close()
    writer.toString
  }

  /**
   * export privateKey to Pem String
   *
   * @return
   */
  def privateKeyToPem(): String = {

    val writer = new StringWriter
    val pemWriter = new JcaPEMWriter(writer)
   cLPublicKey.keyAlgorithm match {
     case KeyAlgorithm.ED25519 =>{
       pemWriter.writeObject(new PemObject("Private KEY", privateKey.asInstanceOf[Ed25519PrivateKeyParameters].getEncoded))
       pemWriter.flush
       pemWriter.close
       writer.toString
     }
     case _ =>{
      val pv =   privateKey.asInstanceOf[ECPrivateKeyParameters]
       val bytes = pv.getD.toByteArray
       null
     }

   }


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
   * load Keypair from pem file
   *
   * @param path
   * @return
   */
  def loadFromPem(path: String): KeyPair = {
    null
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