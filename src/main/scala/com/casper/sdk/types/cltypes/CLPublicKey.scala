package com.casper.sdk.types.cltypes

import com.casper.sdk.json.deserialize.CLPublicKeyDeserializer
import com.casper.sdk.types.cltypes._
import com.casper.sdk.types.cltypes.CLPublicKey.dropAlgorithmBytes
import com.casper.sdk.util.{ByteUtils, HexUtils}
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.casper.sdk.types.cltypes.KeyAlgorithm
import org.bouncycastle.util.io.pem.{PemObject, PemObjectParser}
import org.bouncycastle.openssl.PEMParser
import java.io._
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.PEMParser
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters
import org.bouncycastle.jce.ECNamedCurveTable
import org.bouncycastle.crypto.params.ECDomainParameters
import org.bouncycastle.crypto.params.ECPublicKeyParameters
import java.nio.file.{Files, Paths, FileSystems}
import org.bouncycastle.openssl.jcajce.JcaPEMWriter

import  org.bouncycastle.asn1.x509.AlgorithmIdentifier
import org.bouncycastle.asn1.edec.EdECObjectIdentifiers

import org.bouncycastle.jcajce.provider.asymmetric.edec.BCEdDSAPublicKey
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey
import org.bouncycastle.jce.spec.ECParameterSpec
import org.bouncycastle.jce.spec.ECPublicKeySpec
import com.casper.sdk.crypto.Pem
import java.security.KeyFactory


/**
 * CLPublicKey : Casper system public key
 *
 * @param bytes
 */
@JsonDeserialize(`using` = classOf[CLPublicKeyDeserializer])
class CLPublicKey(
                   override val bytes: Array[Byte]
                 ) extends CLValue(bytes, CLType.PublicKey) with Tag {

  var keyAlgorithm: KeyAlgorithm = null

  /**
   * Constructor using a key bytes array and an Algorithm : 06cA7c39cD272DbF21a86EeB3B36B7c26E2e9b94af64292419f7862936bcA2cA
   *
   * @param key
   * @param algo
   */
  def this(bytes: Array[Byte], keyAlgo: KeyAlgorithm) = {
    this(bytes)
    keyAlgorithm = keyAlgo
    assert(bytes != null)
    assert(keyAlgo != null)
  }

  /**
   * Constructor using a hex account String : 0106cA7c39cD272DbF21a86EeB3B36B7c26E2e9b94af64292419f7862936bcA2cA
   *
   * @param hexKey
   */


  def this(hexKey: String) = this(dropAlgorithmBytes(HexUtils.fromHex(hexKey)), KeyAlgorithm.fromId(hexKey.charAt(1)))

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
    val writer = new StringWriter
    val jcaWriter = new JcaPEMWriter(writer)
    keyAlgorithm match {
      case KeyAlgorithm.ED25519 => {
        val pubKeyInfo = new SubjectPublicKeyInfo(new AlgorithmIdentifier(EdECObjectIdentifiers.id_Ed25519), bytes)
        Pem.toPem(pubKeyInfo)

        /*
        val converter = new JcaPEMKeyConverter().setProvider(new BouncyCastleProvider());
        val pkey = converter.getPublicKey(pubKeyInfo)
        println(pubKeyInfo.getAlgorithm)
        jcaWriter.writeObject(pkey)
        jcaWriter.flush()
        jcaWriter.close()
        writer.toString

         */
      }

      case KeyAlgorithm.SECP256K1 => {

/*
        var curve = ECNamedCurveTable.GetByName("secp256k1");
        var domainParams = new ECDomainParameters(curve.Curve, curve.G, curve.N, curve.H, curve.GetSeed());

        ECPoint q = curve.Curve.DecodePoint(RawBytes);
        ECPublicKeyParameters pk = new ECPublicKeyParameters(q, domainParams);
        writer.WriteObject(pk);
    */

        val eCParameterSpec = ECNamedCurveTable.getParameterSpec("secp256k1")
        val domParam = new ECDomainParameters(eCParameterSpec.getCurve,eCParameterSpec.getG, eCParameterSpec.getN, eCParameterSpec.getH,eCParameterSpec.getSeed)
       // val spec = new ECParameterSpec(eCParameterSpec.getCurve, eCParameterSpec.getG, eCParameterSpec.getN, eCParameterSpec.getH, eCParameterSpec.getSeed)
        val q = eCParameterSpec.getCurve.decodePoint(bytes)

        val pk : ECPublicKeyParameters = new ECPublicKeyParameters(q, domParam);
        Pem.toPem(pk)
        /*
        val   fact = KeyFactory.getInstance("ECDSA", "BC")
        val pkey = fact.generatePublic(new ECPublicKeySpec(q,spec))
        jcaWriter.writeObject(pkey)
        jcaWriter.flush
        jcaWriter.close()
        writer.toString

         */
      }
      case null => throw new IllegalArgumentException("algorithm not handled")
    }
  }

  /**
   * tag =1
   *
   * @return
   */
  override def tag = 1
}

/**
 * Companion object
 */
object CLPublicKey {
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
         val pkey=  converter.getPublicKey(pubkeyInfo)
          pkey match {
             case  ed : BCEdDSAPublicKey => {
              new CLPublicKey(ed.getPointEncoding(), KeyAlgorithm.ED25519)
             }
             case  ec : BCECPublicKey => {
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
