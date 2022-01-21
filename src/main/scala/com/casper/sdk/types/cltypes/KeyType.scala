package com.casper.sdk.types.cltypes

/**
 * TypeKEy enum
 */
enum KeyType(val tag: Int, val prefix : String) {
  case  Account extends KeyType(0,"account-hash")
  case Uref extends KeyType(2,"uref")
  case Hash extends KeyType(1,"hash")
  case Transfer extends KeyType(3,"transfer")
  case DeployInfo extends KeyType(4,"deploy")
  case EraInfo extends KeyType(5,"era")
  case Balance extends KeyType(6,"balance")
  case Bid extends KeyType(7,"bid")
  case Withdraw extends KeyType(8,"withdraw")
}

object KeyType{

  //TODO : refactor with find method
  def getByPrefix(prefix : String):KeyType={

    prefix match {
      case  "transfer"=> Transfer
      case  "account-hash"=> Account
      case  "hash"=> Hash
      case  "uref"=> Uref
      case  "balance"=> Balance
      case  "Bid"=> Bid
      case  "withdraw"=> Withdraw
      case  "deploy"=> DeployInfo
      case  "era"=> EraInfo
      case _ => throw IllegalArgumentException("invalid prefix for Key value")
    }
  }
}