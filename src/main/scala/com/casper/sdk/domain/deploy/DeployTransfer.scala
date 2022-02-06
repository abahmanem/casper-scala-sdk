package com.casper.sdk.domain.deploy

import com.casper.sdk.types.cltypes.{AccountHash, CLType, CLTypeInfo, CLValue}

/**
 * DeployTransfer Entity
 *
 * @param args
 */
case class DeployTransfer(
                      override val args: Seq[Seq[DeployNamedArg]]
                    ) extends DeployExecutable(args) {
  def tag = 5
}

/**
 * companion object
 */

object DeployTransfer {

  /**
   * Trnasfert to an accountHash
   *
   * @param amount to transfer
   * @param to     recipient account
   * @param id
   * @return DeployTransfer
   */
  def apply(amount: Long, to: AccountHash, id: BigInt): DeployTransfer = {
    var args = Seq[DeployNamedArg]()
    args = args.+:(new DeployNamedArg("amount", CLValue.U512(amount)))
      .+:(new DeployNamedArg("target", CLValue.ByteArray(to.bytes)))
      .+:(new DeployNamedArg("id", if (id == null) CLValue.OptionNone(new CLTypeInfo(CLType.U64)) else CLValue.Option(CLValue.U64(id))))
    new DeployTransfer(Seq(args))
  }
}
