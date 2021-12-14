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
  val storedValue = client.getStateItem("30cE5146268305AeeFdCC05a5f7bE7aa6dAF187937Eed9BB55Af90e1D49B7956","account-hash-46dE97966cfc2F00C326e654baD000AB7a5E26bEBc316EF4D74715335cF32A88",Seq.empty)
  client.getBalance("30cE5146268305AeeFdCC05a5f7bE7aa6dAF187937Eed9BB55Af90e1D49B7956",new URef("uref-9cC6877ft07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d067C4D0363-007"))

  assert(storedValue.Contract == null)

  assert(storedValue.Account != null)

  println("kkkk"+storedValue.Account.main_purse.format.toLowerCase)
  assert(storedValue.Account.main_purse.format.toLowerCase== "uref-9cC68775d07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d067C4D0363-007".toLowerCase)




  val u512 =
    """  {
      |                                "cl_type": "U512",
      |                                "bytes": "0400ca9A3B",
      |                                "parsed": "1000000000"
      |                            }""".stripMargin

  val value = JsonConverter.fromJson[CLValue](u512)

  assert(value != null)

  assert(value.parsed == "1000000000")

  assert(value.cl_infoType.cl_Type == CLType.U512)

  assert(value.bytes.sameElements(HexUtils.fromHex("0400ca9A3B")))




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
