package com.casper.sdk.rpc

import com.casper.sdk.domain
import com.casper.sdk.CasperSdk
import com.casper.sdk.domain.deploy.{DeployNamedArg, DeployTransfer}
import com.casper.sdk.domain.deploy
import com.casper.sdk.util.{ByteUtils, JsonConverter}


import com.casper.sdk.types.cltypes.{AccessRight, AccountHash, CLValue, URef}
import org.scalactic.Prettifier.default
object MainnetTester  extends  App {

  val client = new CasperSdk("http://65.108.1.10:7777/rpc")
  val peers = client.getPeers()

  val rst = client.getStateRootHash("")

  val blc = client.getBlock("4f813f035477f0a497a41956d6b3b6ba17909a2a030e55b0ed30d88a43371d98")
  val deploy = client.getDeploy("541db942d6bf519f57e685c30e84047f2b59b0715f54d10ab0b5a6d0d0e80632")


}
