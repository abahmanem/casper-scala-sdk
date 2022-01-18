package com.casper.sdk.crypto

import org.bouncycastle.crypto.util.{PrivateKeyFactory, PrivateKeyInfoFactory, PublicKeyFactory, SubjectPublicKeyInfoFactory}
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo
import org.bouncycastle.asn1.x509.{AlgorithmIdentifier, SubjectPublicKeyInfo}
import org.bouncycastle.crypto.params.{AsymmetricKeyParameter, DSAKeyParameters, ECKeyParameters, RSAKeyParameters}
import org.bouncycastle.openssl.jcajce.JcaPEMWriter
import java.io.StringWriter
import java.io.StringReader
import java.io.IOException
import org.bouncycastle.crypto.AsymmetricCipherKeyPair
import org.bouncycastle.openssl.{PEMKeyPair, PEMParser}

/**
 * Pem utility object
 */
object Pem {

  /**
   * Writes key objects to pem strings
   *
   * @param data : data to write into pem String
   * @throws IOException
   * @return Pem String
   */
  @throws[IOException]
  def toPem(data: AnyRef): String = {
    val stringWriter = new StringWriter(4096)
    val writer = new JcaPEMWriter(stringWriter)
    try {
      data match {

        case keyPair: AsymmetricCipherKeyPair =>  writer.writeObject(new PEMKeyPair(BCConvert.toSubjectPublicKeyInfo(keyPair.getPublic), BCConvert.toPrivateKeyInfo(keyPair.getPrivate)))

        case key: AsymmetricKeyParameter if key.isPrivate =>  writer.writeObject(BCConvert.toPrivateKeyInfo(key))

        case key: AsymmetricKeyParameter if !key.isPrivate =>   writer.writeObject(BCConvert.toSubjectPublicKeyInfo(key))

        case _ => writer.writeObject(data)

      }
      writer.flush()
      stringWriter.toString
    } finally {
      writer.close()
    }
  }
}

/**
 * Reads from Pem String
 *
 * @param data
 * @param offset
 * @throws
 * @return
 */
@throws[IOException]
def fromPem(data: String, offset: Int = 0): AnyRef = {
  val reader = new PEMParser(new StringReader(data))
  try {
    for (_ ← 0 until offset) reader.readObject()
    reader.readObject()
  } finally {
    reader.close()
  }
}
