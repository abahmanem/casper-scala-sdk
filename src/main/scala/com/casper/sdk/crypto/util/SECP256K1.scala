package com.casper.sdk.crypto.util

import com.casper.sdk.crypto.KeyPair
import com.casper.sdk.domain.DeployHash
import com.casper.sdk.util.HexUtils
import org.bouncycastle.asn1.x9.{X9ECParameters, X9IntegerConverter}
import org.bouncycastle.crypto.digests.SHA256Digest
import org.bouncycastle.crypto.ec.CustomNamedCurves
import org.bouncycastle.crypto.params.{ECDomainParameters, ECPrivateKeyParameters, ECPublicKeyParameters, ParametersWithRandom}
import org.bouncycastle.crypto.signers.{DSADigestSigner, ECDSASigner, HMacDSAKCalculator, PlainDSAEncoding}
import org.bouncycastle.jcajce.provider.asymmetric.ec.{BCECPrivateKey, BCECPublicKey}
import org.bouncycastle.math.ec.{ECAlgorithms, ECPoint, FixedPointCombMultiplier}
import org.bouncycastle.math.ec.custom.sec.SecP256K1Curve

import java.math.BigInteger
import java.security.{MessageDigest, SecureRandom}

/**
 * SECP256K1 keys object utility
 */

object SECP256K1 {
  val CURVE_PARAMS = CustomNamedCurves.getByName("secp256k1")
  val CURVE = new ECDomainParameters(CURVE_PARAMS.getCurve(), CURVE_PARAMS.getG(), CURVE_PARAMS.getN(), CURVE_PARAMS.getH())
  val HALF_CURVE_ORDER = CURVE_PARAMS.getN().shiftRight(1)

  /**
   * sign a message
   * @param msg : message to sign
   * @param keyPair : keypair
   * @return  signature byte array
   */
  def sign(msg: Array[Byte], keyPair: KeyPair): Array[Byte] = {
    require(msg!=null &&  keyPair!=null)
    val signer = new DSADigestSigner(new ECDSASigner(), new SHA256Digest(), PlainDSAEncoding.INSTANCE)
    val pivk = keyPair.privateKey.asInstanceOf[BCECPrivateKey].getD
    val param = new ParametersWithRandom(new ECPrivateKeyParameters(pivk, CURVE), new SecureRandom())
    signer.init(true, param)
    signer.update(msg, 0, msg.length)
    signer.generateSignature()
  }

  /**
   * verify signature
   * @param msg msg
   * @param signature signature
   * @param publickey public key
   * @return true if the signature is verified or false
   */
  def verify(msg: Array[Byte], signature: Array[Byte], publickey: Array[Byte]): Boolean = {
    require(msg!=null &&  signature!=null &&  publickey!=null)
    val ecPoint = CURVE.getCurve.decodePoint(publickey)
    val ecPkparam = new ECPublicKeyParameters(ecPoint, CURVE)
    val signer = new DSADigestSigner(new ECDSASigner(), new SHA256Digest(), PlainDSAEncoding.INSTANCE)
    signer.init(false, ecPkparam)
    signer.update(msg, 0, msg.length)
    signer.verifySignature(signature)
  }
}