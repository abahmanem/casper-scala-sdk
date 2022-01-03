package com.casper.sdk.domain.deploy

import com.casper.sdk.types.cltypes.{CLPublicKey, CLValue}
/**
 * DeployNamedArg entiyt class
 */
case class DeployNamedArg(
                          val name:String,
                          val value:CLValue
                         ){

  /**
   * constructors with CLValues
   * @param name
   * @param long
   */
  def this(name:String, byte : Byte) = this(name,CLValue.U8(byte))
  def this(name:String, long : Long) = this(name,CLValue.I64(long))
  def this(name:String, bool : Boolean) = this(name,CLValue.Bool(bool))
  def this(name:String, int : Int) = this(name,CLValue.I32(int))
  def this(name:String, byteArr : Array[Byte]) = this(name,CLValue.ByteArray(byteArr))
  def this(name:String, bigint : BigInt) = this(name,CLValue.U512(bigint))
  def this(name:String, str : String) = this(name,CLValue.String(str))
}
