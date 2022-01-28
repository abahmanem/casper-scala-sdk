package com.casper.sdk.rpc

import com.casper.sdk.domain
import com.casper.sdk.CasperSdk
import com.casper.sdk.domain.deploy.{DeployNamedArg, DeployTransfer}
import com.casper.sdk.domain.deploy
import com.casper.sdk.util.{ByteUtils, JsonConverter}
import com.casper.sdk.util.implicits.idInstance

import com.casper.sdk.types.cltypes.{AccessRight, AccountHash, CLValue, URef}
import org.scalactic.Prettifier.default
object MainnetTester  extends  App {

  val client = new CasperSdk("http://65.21.202.120:7777/rpc")
  val stateRootHash = client.getStateRootHash("7ededrtzg9fffff700e6baed36e8cb99400da0449fae6c95c")
}
