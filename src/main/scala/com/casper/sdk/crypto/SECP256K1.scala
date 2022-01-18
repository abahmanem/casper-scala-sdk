package com.casper.sdk.crypto

import org.bouncycastle.asn1.sec.SECNamedCurves
import org.bouncycastle.asn1.{ASN1InputStream, ASN1Integer, DERSequenceGenerator, DLSequence}
import org.bouncycastle.crypto.digests.SHA256Digest
import org.bouncycastle.crypto.params.{ECDomainParameters, ECKeyGenerationParameters, ECPrivateKeyParameters, ECPublicKeyParameters}
import org.bouncycastle.crypto.signers.{ECDSASigner, HMacDSAKCalculator}
import org.bouncycastle.math.ec.ECPoint

import java.io.{ByteArrayOutputStream, IOException}


/**
 * SECP256K1 object utility
 */
object SECP256K1 {

  val curve = SECNamedCurves.getByName("secp256k1")
  val domain = new ECDomainParameters(curve.getCurve(), curve.getG(), curve.getN(),
    curve.getH())

  /**
   * verify a signature
   *
   * @param msg       signed msg
   * @param signature signature to verify
   * @param publicKey key to verifiy msg against
   * @return true if the signature is verified or false if not
   */
  def verify(msg: Array[Byte], signature: Array[Byte], publicKey: Array[Byte]): Boolean = {

    val q = curve.getCurve.decodePoint(publicKey)
    val signer = new ECDSASigner()
    signer.init(false, new ECPublicKeyParameters(q, domain))
    val asn1 = new ASN1InputStream(signature)
    try {
      val seq = asn1.readObject().asInstanceOf[DLSequence]
      signer.verifySignature(msg, seq.getObjectAt(0).asInstanceOf[ASN1Integer].getPositiveValue, seq.getObjectAt(1).asInstanceOf[ASN1Integer].getPositiveValue)
    }
    catch {
      case io: IOException => false
    }
    finally {
      asn1.close()
    }
  }

  /**
   * sign a message
   *
   * @param msg        message to sign
   * @param privateKey privateKey to use t osign the message
   * @return signature byte array
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


