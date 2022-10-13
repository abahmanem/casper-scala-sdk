package com.casper.sdk.crypto.util

import com.casper.sdk.crypto.KeyPair
import com.casper.sdk.util.{ByteUtils, HexUtils}
import org.bouncycastle.asn1.x9.{X9ECParameters, X9IntegerConverter}

import org.bouncycastle.crypto.ec.CustomNamedCurves
import org.bouncycastle.crypto.params.{ECDomainParameters, ECPrivateKeyParameters, ECPublicKeyParameters, ParametersWithRandom}
import org.bouncycastle.crypto.signers.{DSADigestSigner, ECDSASigner, HMacDSAKCalculator, PlainDSAEncoding}
import org.bouncycastle.jcajce.provider.asymmetric.ec.{BCECPrivateKey, BCECPublicKey}
import org.bouncycastle.math.ec.{ECAlgorithms, ECPoint, FixedPointCombMultiplier}
import org.bouncycastle.math.ec.custom.sec.SecP256K1Curve
import org.bouncycastle.crypto.digests.SHA256Digest
import org.bouncycastle.crypto.digests.SHA256Digest
import java.math.BigInteger
import java.security.{MessageDigest, SecureRandom}
import java.nio.ByteBuffer
import scala.util.Try

/**
 * SECP256K1 keys object utility
 */

object SECP256K1 {
  val CURVE_PARAMS = CustomNamedCurves.getByName("secp256k1")
  val CURVE = new ECDomainParameters(CURVE_PARAMS.getCurve(), CURVE_PARAMS.getG(), CURVE_PARAMS.getN(), CURVE_PARAMS.getH())
  val HALF_CURVE_ORDER = CURVE_PARAMS.getN().shiftRight(1)

  /**
   * sign a message
   *
   * @param msg     : message to sign
   * @param keyPair : keypair
   * @return signature byte array
   */
  def sign(msg: Array[Byte], keyPair: KeyPair): Either[Throwable, Array[Byte]] = Try {
    val signer = new ECDSASigner(new HMacDSAKCalculator(new SHA256Digest))
    val privKey = new ECPrivateKeyParameters(keyPair.privateKey.asInstanceOf[BCECPrivateKey].getD, CURVE)
    signer.init(true, privKey)
    val components: Array[BigInteger] = signer.generateSignature(msg)
    val cannonicalS = if (components(1).compareTo(HALF_CURVE_ORDER) > 0) (CURVE.getN().subtract(components(1))) else components(1)
    val r: Array[Byte] = ByteUtils.padBytes(components(0), 32).get
    val s: Array[Byte] = ByteUtils.padBytes(cannonicalS, 32).get

    val bb = ByteBuffer.allocate(r.length + s.length)
    bb.put(r)
    bb.put(s)
    bb.array()
  }.toEither

  /**
   * verify signature
   *
   * @param msg       msg
   * @param signature signature
   * @param publickey public key
   * @return true if the signature is verified or false
   */
  def verify(msg: Array[Byte], signature: Array[Byte], publickey: Array[Byte]): Either[Throwable, Boolean] = Try {
    val ecPoint = CURVE.getCurve.decodePoint(publickey)
    val ecPkparam = new ECPublicKeyParameters(ecPoint, CURVE)
    val signer = new DSADigestSigner(new ECDSASigner(), new SHA256Digest(), PlainDSAEncoding.INSTANCE)
    signer.init(false, ecPkparam)
    signer.update(msg, 0, msg.length)
    signer.verifySignature(signature)
  }.toEither
}