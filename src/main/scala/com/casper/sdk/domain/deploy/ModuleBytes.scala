package com.casper.sdk.domain.deploy

/**
 *
 * @param modules_bytes
 * @param args
 */
case class ModuleBytes (
                    val modules_bytes :Array[Byte],
                    override val args: Seq[Seq[DeployNamedArg]]
                  ) extends DeployExecutable(args) {

  override def  encode() : Array[Byte] ={
    null
  }
}


