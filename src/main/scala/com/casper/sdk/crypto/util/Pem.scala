package com.casper.sdk.crypto.util

import org.bouncycastle.crypto.AsymmetricCipherKeyPair
import org.bouncycastle.crypto.params.AsymmetricKeyParameter
import org.bouncycastle.openssl.PEMKeyPair
import org.bouncycastle.openssl.jcajce.JcaPEMWriter

import java.io.{IOException, StringWriter}

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

        case keyPair: AsymmetricCipherKeyPair => writer.writeObject(new PEMKeyPair(BCConvert.toSubjectPublicKeyInfo(keyPair.getPublic), BCConvert.toPrivateKeyInfo(keyPair.getPrivate)))

        case key: AsymmetricKeyParameter if key.isPrivate => writer.writeObject(BCConvert.toPrivateKeyInfo(key))

        case key: AsymmetricKeyParameter if !key.isPrivate => writer.writeObject(BCConvert.toSubjectPublicKeyInfo(key))

        case _ => writer.writeObject(data)

      }
      writer.flush()
      stringWriter.toString
    } finally {
      writer.close()
    }
  }
}
