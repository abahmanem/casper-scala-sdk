package com.casper.sdk.types.cltypes

/**
 * TypeKEy enum
 */
enum KeyType(val tag: Int, val prefix : String) {
  case Account extends KeyType(0,"account-hash")
  case URef extends KeyType(2,"uref")
  case Hash extends KeyType(1,"hash")
  case Transfer extends KeyType(3,"transfer")
  case DeployInfo extends KeyType(4,"deploy")
  case EraInfo extends KeyType(5,"era")
  case Balance extends KeyType(6,"balance")
  case Bid extends KeyType(7,"bid")
  case Withdraw extends KeyType(8,"withdraw")
  case Dictionary extends KeyType(9, "dictionary")
  case Unbond extends KeyType(10, "unbond")
  case SystemContractRegistry extends KeyType(11, "registry")
  case ChainspecRegistry extends KeyType(12, "chain")

}

/**
 * caompanion object
 */
object KeyType{

  /**
   * search KeyValue with prefix
   * @param prefix
   * @return
   */
  def getByPrefix(prefix : String):Option[KeyType]=
    KeyType.values.find(_.prefix == prefix) match {
      case Some(a)=> Some(a)
      case _ => None
    }
}