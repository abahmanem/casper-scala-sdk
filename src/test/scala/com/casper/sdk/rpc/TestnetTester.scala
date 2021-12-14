package com.casper.sdk.rpc

import com.casper.sdk.domain
import com.casper.sdk.CasperSdk
import com.casper.sdk.domain.deploy.{DeployNamedArg, DeployTransfer}
import com.casper.sdk.domain.deploy
import com.casper.sdk.util.{ByteUtils, HexUtils, JsonConverter}
import com.casper.sdk.util.implicits.idInstance
import scodec.bits.ByteVector
import scodec.bits.hex
import com.casper.sdk.types.cltypes.{AccessRight, AccountHash, CLPublicKey, CLType, CLValue, KeyAlgorithm, URef}
import org.scalactic.Prettifier.default
object TestnetTester  extends  App {


  val client = new CasperSdk("http://65.21.227.180:7777/rpc")
  val hexkey =  "017f747b67bd3fe63c2a736739dfe40156d622347346e70f68f51c178a75ce5537"
  val jsonkey =  """ "017f747b67bd3fe63c2a736739dfe40156d622347346e70f68f51c178a75ce5537" """
  println(KeyAlgorithm.matchKeyWithAlgo(KeyAlgorithm.ED25519,hexkey))
  val pubKey = JsonConverter.fromJson[CLPublicKey](jsonkey)
  println(pubKey.formatAsHexAccount)
  assert(pubKey!=null)

  val p = new CLPublicKey("017f747b67bd3fe63c2a736739dfe40156d622347346e70f68f51c178a75ce5537")
  assert(pubKey.bytes.sameElements(p.bytes))
  println(p.formatAsHexAccount)




}
