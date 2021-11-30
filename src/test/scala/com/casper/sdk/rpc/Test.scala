package com.casper.sdk.rpc

import com.casper.sdk.domain.*
import com.casper.sdk.CasperSdk
import com.casper.sdk.domain.deploy.{DeployNamedArg, DeployTransfer}
import com.casper.sdk.domain.deploy.*
import com.casper.sdk.util.JsonConverter
import com.casper.sdk.util.implicits.idInstance
import scodec.bits.ByteVector
import scodec.bits.hex
import com.casper.sdk.types.cltypes.CLValue
object Test  extends  App {


  val client = new CasperSdk("http://65.21.202.120:7777/rpc")

    val stateRootHash = client.getStateRootHash("7ededrtzg9fffff700e6baed36e8cb99400da0449fae6c95c")
  println(stateRootHash)



  //val client = new CasperSdk("http://65.21.202.120:7777/rpc")

  println(client.getPeers())
  println(client.getStateRootHash(""))
  println(client.getBlock("995ea06c3569bd8cba2759c45db7f2f0d5d7d9ceb11adfa41260d39c2af15ccd"))
  println(client.getAuctionInfo("995ea06c3569bd8cba2759c45db7f2f0d5d7d9ceb11adfa41260d39c2af15ccd"))
  println(client.getDeploy("17a68cdf2b6829bcf41ee53e73d086b43315c67a5afaa30044fdf3c5000ad186"))
  println(client.getEraInfoBySwitchBlock("e47cbf904abc1258399699d67d85030d5e987324955c775b3fbb633abd4fd43f"))
  //println(client.getPeers())
  //println(client.getPeers())

}
