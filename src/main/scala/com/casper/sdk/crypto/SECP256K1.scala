package com.casper.sdk.crypto

import org.bouncycastle.asn1.{ASN1InputStream, ASN1Integer, DERSequenceGenerator, DLSequence}
import org.bouncycastle.asn1.sec.SECNamedCurves
import org.bouncycastle.asn1.x9.X9ECParameters
import org.bouncycastle.crypto.AsymmetricCipherKeyPair
import org.bouncycastle.crypto.digests.SHA256Digest
import org.bouncycastle.crypto.generators.ECKeyPairGenerator
import org.bouncycastle.crypto.params.{ECDomainParameters, ECKeyGenerationParameters, ECPrivateKeyParameters, ECPublicKeyParameters}
import org.bouncycastle.crypto.signers.{ECDSASigner, HMacDSAKCalculator}
import org.bouncycastle.math.ec.ECPoint

import java.io.{ByteArrayOutputStream, IOException}
import java.math.BigInteger

/**
 *  SECP256K1 object utility
 */
object SECP256K1 {


  val curve = SECNamedCurves.getByName("secp256k1")
  val domain = new ECDomainParameters(curve.getCurve(), curve.getG(), curve.getN(),
    curve.getH())

  /**
   *
   * @param msg
   * @param signature
   * @param publicKey
   * @return
   */
  def verify(msg: Array[Byte], signature: Array[Byte], publicKey: Array[Byte]): Boolean = {

    val curve = SECNamedCurves.getByName("secp256k1")
    val domParams = new ECDomainParameters(curve.getCurve, curve.getG, curve.getN, curve.getH, curve.getSeed)
    val q = curve.getCurve.decodePoint(publicKey)
    val params = new ECPublicKeyParameters(q, domParams)
    val signer = new ECDSASigner()
    signer.init(false, params)
    val asn1 = new ASN1InputStream(signature)
    try {
      val seq = asn1.readObject().asInstanceOf[DLSequence]
      val r = seq.getObjectAt(0).asInstanceOf[ASN1Integer].getPositiveValue
      val s = seq.getObjectAt(1).asInstanceOf[ASN1Integer].getPositiveValue
      signer.verifySignature(msg, r, s)
    }
    catch {
      case io: IOException => false
    }
    finally {
      asn1.close()
    }
  }

    /**
     *
     * @param msg
     * @param privateKey
     * @return
     */
    def sign(msg: Array[Byte], params: ECPrivateKeyParameters): Array[Byte] = {
      val signer = new ECDSASigner(new HMacDSAKCalculator(new SHA256Digest()))
      signer.init(true, params)
      val signature = signer.generateSignature(msg)
      val baos = new ByteArrayOutputStream()
      try {
        val seq: DERSequenceGenerator = new DERSequenceGenerator(baos)
        seq.addObject(new ASN1Integer(signature(0)))
        seq.addObject(new ASN1Integer(signature(1)))
        seq.close
        baos.toByteArray
      }
      catch {
        case io: IOException => null
      }
    }
}


