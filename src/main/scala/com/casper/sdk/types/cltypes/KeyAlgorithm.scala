package com.casper.sdk.types.cltypes


/**
 * KeyAlgorithm Enum
 */

enum KeyAlgorithm(val bits: Int) {
  case ED25519 extends KeyAlgorithm(1)
  case SECP256K1 extends KeyAlgorithm(2)
}

object KeyAlgorithm {

  /**
   * get Algorithm from a hex account key : 0106cA7c39cD272DbF21a86EeB3B36B7c26E2e9b94af64292419f7862936bcA2cA
   *
   * @param hexAccountKey
   * @return
   */
  def fromHexAccount(hexAccountKey: String): Option[KeyAlgorithm] = hexAccountKey.substring(0, 2) match {
      case "01" => Some(KeyAlgorithm.ED25519)
      case "02" => Some(KeyAlgorithm.SECP256K1)
      case _ => None
    }
  /**
   * get KeyAlgorithm from an id
   *
   * @param id
   * @return
   */
  def fromId(id: Int): Option[KeyAlgorithm] =   KeyAlgorithm.values.find(_.bits == id)
}