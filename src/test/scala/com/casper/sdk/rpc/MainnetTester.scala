package com.casper.sdk.rpc

import com.casper.sdk.domain
import com.casper.sdk.CasperSdk
import com.casper.sdk.domain.deploy.{DeployNamedArg, DeployTransfer}
import com.casper.sdk.domain.deploy
import com.casper.sdk.util.{ByteUtils, JsonConverter}
import com.casper.sdk.util.implicits.idInstance
import scodec.bits.ByteVector
import scodec.bits.hex
import com.casper.sdk.types.cltypes.{AccessRight, AccountHash, CLValue, URef}
import org.scalactic.Prettifier.default
object MainnetTester  extends  App {


  val client = new CasperSdk("http://65.21.202.120:7777/rpc")

   val stateRootHash = client.getStateRootHash("7ededrtzg9fffff700e6baed36e8cb99400da0449fae6c95c")
  println(client.getPeers())
  println(client.getStateRootHash(""))
  println(client.getBlock("995ea06c3569bd8cba2759c45db7f2f0d5d7d9ceb11adfa41260d39c2af15ccd"))
  println(client.getAuctionInfo("995ea06c3569bd8cba2759c45db7f2f0d5d7d9ceb11adfa41260d39c2af15ccd"))
  println(client.getDeploy("17a68cdf2b6829bcf41ee53e73d086b43315c67a5afaa30044fdf3c5000ad186"))
  client.getBlock("fr85r8745F6aCe634eD82aC54545488e597d55920Ff1dE8e5dfffdfd9558736EF570d")
  println(JsonConverter.toJson(Map("Uref" -> Map("dictionary_item_key" -> "dgfg" , "seed_uref"->"xcgfdgf"))))
 //Account
  val SS = client.getStateItem("967d608f02a2710bf550878ba28774c87c448a3618aee5900d2cdbf28162366d","account-hash-85148dcd6c54b77e462a9acf387fb05aca953a83011db2c601716de0af1cf47c"
    ,Seq.empty)

  println(SS)
 //Contratc
  val SS1 = client.getStateItem("9ae48819f18df15184e0c353d025d03441189be54f6487e638ced1d48ab1f133","hash-d2469afeb99130f0be7c9ce230a84149e6d756e306ef8cf5b8a49d5182e41676"
    ,Seq.empty)

  println(SS1)

  //CLValue
  val SS2 = client.getStateItem("967d608f02a2710bf550878ba28774c87c448a3618aee5900d2cdbf28162366d",
    "uref-51215724cc359a60797f64d88543002a069176f3ea92d4c37d31304e2849ef13-004"
    ,Seq.empty)
  println(SS2)


  //GeBalance
  val SS3 = client.getBalance("967d608f02a2710bf550878ba28774c87c448a3618aee5900d2cdbf28162366d",
     URef("uref-51215724cc359a60797f64d88543002a069176f3ea92d4c37d31304e2849ef13-004"))
  println(SS3)


  //Get_Dic
  val SS4 = client.getDictionaryItem("d5811c438982f231a9152011c0f6ce9ae9c716e8075a6edec8390f10072ecd29",
    "f870e3cadfde21d7d7686fdf3d1a8413838274d363ca7b27ae71fc9125eb6743",
   "uref-0d689e987db7ee5be246282c3a7badf0411e34baeeab8e9d73c1223ae4ad02e5-007")
  println(SS4)

  val SS5 = client.getRpcSchema()
  println(SS5)



}
