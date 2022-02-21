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
  def fromHexAccount(hexAccountKey: String): KeyAlgorithm = {
    hexAccountKey.substring(0, 2) match {
      case "01" => KeyAlgorithm.ED25519
      case "02" => KeyAlgorithm.SECP256K1
      case _ => throw new IllegalArgumentException(hexAccountKey + " is not a valid  byte tag in casper public key system")
    }
  }

  /**
   * match hex key account to an algorithm
   *
   * @param algo
   * @param key
   * @return
   */
  def matchKeyWithAlgo(algorithm: KeyAlgorithm, hexAccountKey: String): Boolean = {
    assert(hexAccountKey != null)
    (hexAccountKey == null || hexAccountKey.length < 2) match {
      case true => false
      case false => algorithm == KeyAlgorithm.ED25519 && hexAccountKey.substring(0, 2) == "01" || algorithm == KeyAlgorithm.SECP256K1 && hexAccountKey.substring(0, 2) == "02"
    }
  }

  /**
   * get KeyAlgorithm from an id
   *
   * @param id
   * @return
   */
  def fromId(id: Int): Option[KeyAlgorithm] = {

    KeyAlgorithm.values.find(_.bits == id) match {
      case Some(a) => Some(a)
      case _ => None //throw new IllegalArgumentException("CLPublicKey : Unknown algorithm Id " + id)
    }
  }
}