package com.casper.sdk.domain.deploy

import com.casper.sdk.types.cltypes.{CLType, CLTypeInfo, CLValue}

/**
 *
 * @param modules_bytes
 * @param args
 */
case class ModuleBytes(
                        val modules_bytes: Array[Byte],
                        override val args: Seq[Seq[DeployNamedArg]]
                      ) extends DeployExecutable(args) {
  override def tag = 0
}

object ModuleBytes {

  /**
   * create a ModuleBytes with a payment amount
   *
   * @param amount
   * @return
   */
  def apply(amount: BigInt): ModuleBytes = {
    var args = Seq[DeployNamedArg]()
    args = args.+:(new DeployNamedArg("amount", CLValue.U512(amount)))
    new ModuleBytes(Array.empty[Byte], Seq(args))
  }
}

