package com.casper.sdk.rpc

import com.casper.sdk.domain
import com.casper.sdk.CasperSdk
import com.casper.sdk.domain.deploy.{DeployNamedArg, DeployTransfer}
import com.casper.sdk.domain.deploy
import com.casper.sdk.util.{ByteUtils, HexUtils, JsonConverter}
import com.casper.sdk.util.implicits.idInstance
import scodec.bits.ByteVector
import scodec.bits.hex
import com.casper.sdk.types.cltypes.{AccessRight, AccountHash, CLPublicKey, CLValue, URef}
import org.scalactic.Prettifier.default
object TestnetTester  extends  App {


  val client = new CasperSdk("http://65.21.227.180:7777/rpc")
  //client.getBalance("30cE5146268305AeeFdCC05a5f7bE7aa6dAF187937Eed9BB55Af90e1D49B7956",new URef("9cC6877ft07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d067C4D0363-007"))

 // println(HexUtils.bytesToHex(new CLPublicKey("01c6d11a0fa563f8cc3ed5e967d5901c80004bdcde6250ddea18af2b4eae0a902d").bytes))


}
