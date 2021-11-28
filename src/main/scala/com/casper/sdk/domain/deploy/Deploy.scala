package com.casper.sdk.domain.deploy

case class Deploy (
                   val hash:String,
                   val header :DeployHeader,
                   val payment:DeployExecutable,
                   val session:DeployExecutable,
                   val approvals:Set[DeployApproval])
extends BytesSerializable {

  override def  encode() : Array[Byte] ={
    null
  }
}

