package com.casper.sdk.rpc

import com.casper.sdk.CasperSdk
import com.casper.sdk.crypto.hash.Hash
import com.casper.sdk.domain.deploy.{DeployHeader, DeployNamedArg, StoredContractByHash, Transfer}
import com.casper.sdk.rpc.http.HttpRPCService
import com.casper.sdk.rpc.params.{HashBlockIdentifier, HeightBlockIdentifier, StateRootHashIdentifier}
import com.casper.sdk.util.{CirceConverter, HexUtils}
import com.casper.sdk.rpc.result.{AuctionStateResult, BlockResult, DeployResult, TransferResult}
import com.casper.sdk.serialization.domain.deploy.DeployHeaderByteSerializer
import com.casper.sdk.types.cltypes.{CLPublicKey, CLTypeInfo, CLValue, URef}
import io.circe.HCursor

import scala.util.Try
object Test extends  App{

  val mainnetclient = new CasperSdk("https://node-clarity-mainnet.make.services/rpc")

  val testnetclient = new CasperSdk("https://node-clarity-testnet.make.services/rpc")

  val balance = testnetclient.getBalance("30cE5146268305AeeFdCC05a5f7bE7aa6dAF187937Eed9BB55Af90e1D49B7956", "9cC6877ft07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d067C4D0363-007")


  //val storedValue = testnetclient.queryGlobalState(StateRootHashIdentifier("808ad642fefe6a0d3cadfb151a39aecb37183121ae20565ab32f5c04db20513e"), "hash-1c1545ab3bdbe0df3823a53c8160fc1960847cd3008376701f73e5e3ff13bbc9", Seq.empty)


  println(balance)

  /*



  case class T0(nam:String)
  object T0{

    import io.circe.syntax._
    import io.circe.{Decoder, Encoder}
    import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

    implicit val decoder: Decoder[T0] = deriveDecoder[T0]
    implicit val encoder: Encoder[T0] = deriveEncoder[T0]
  }

  val t=T0("dvdsdf")

  println(CirceConverter.toJson(t))

  println(Try(t.nam.getBytes()).toOption)


  val serializer = new DeployHeaderByteSerializer()
  val header = new DeployHeader(CLPublicKey("01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900c"),
    "2022-10-05T16:17:42.822Z", "30m", 1,
    Hash("4811966d37fe5674a8af4001884ea0d9042d1c06668da0c963769c3a01ebd08f"),
    Seq(Hash("0101010101010101010101010101010101010101010101010101010101010101").get),
    "casper-example")

  println(HexUtils.toHex(serializer.toBytes(header).get).get)



  val dpl = """{              "api_version": "1.4.8",
              |            "deploy": {
              |            "hash": "48ea311f77078c18bbb5223382147cd3d9eda1cc5e5dd3388f261f90bddb48c5",
              |            "header": {
              |                "account": "0202da8a27ffcc5669e7d0200179e70c2eaeedae93b206f13cce1ec9d8f94c77b9a5",
              |                "timestamp": "2022-09-24T18:26:55.737Z",
              |                "ttl": "30m",
              |                "gas_price": 1,
              |                "body_hash": "65cb57f1472afe0a8879ad9154af82581f086cfdb3514d3a7f8762c29b2005d1",
              |                "dependencies": [],
              |                "chain_name": "casper"
              |            },
              |            "payment": {
              |                "ModuleBytes": {
              |                    "module_bytes": "",
              |                    "args": [
              |                        [
              |                            "amount",
              |                            {
              |                                "cl_type": "U512",
              |                                "bytes": "0400f90295",
              |                                "parsed": "2500000000"
              |                            }
              |                        ]
              |                    ]
              |                }
              |            },
              |            "session": {
              |                "StoredContractByHash": {
              |                    "hash": "ccb576d6ce6dec84a551e48f0d0b7af89ddba44c7390b690036257a04a3ae9ea",
              |                    "entry_point": "delegate",
              |                    "args": [
              |                        [
              |                            "delegator",
              |                            {
              |                                "cl_type": "PublicKey",
              |                                "bytes": "0202da8a27ffcc5669e7d0200179e70c2eaeedae93b206f13cce1ec9d8f94c77b9a5",
              |                                "parsed": "0202da8a27ffcc5669e7d0200179e70c2eaeedae93b206f13cce1ec9d8f94c77b9a5"
              |                            }
              |                        ],
              |                        [
              |                            "validator",
              |                            {
              |                                "cl_type": "PublicKey",
              |                                "bytes": "01c377281132044bd3278b039925eeb3efdb9d99dd5f46d9ec6a764add34581af7",
              |                                "parsed": "01c377281132044bd3278b039925eeb3efdb9d99dd5f46d9ec6a764add34581af7"
              |                            }
              |                        ],
              |                        [
              |                            "amount",
              |                            {
              |                                "cl_type": "U512",
              |                                "bytes": "060038d360ba01",
              |                                "parsed": "1900000000000"
              |                            }
              |                        ]
              |                    ]
              |                }
              |            },
              |            "approvals": [
              |                {
              |                    "signer": "0202da8a27ffcc5669e7d0200179e70c2eaeedae93b206f13cce1ec9d8f94c77b9a5",
              |                    "signature": "02f5155f5b36750ca24524affd92ddf3a3025ab299d3bb65930c3198c6a67c6181193bc9954efca9781a7d15849369826ed771e48286b722499b913883255eb51d"
              |                }
              |            ]
              |        }
              |}
              |
              |
              |""".stripMargin

  import cats.syntax.either._
  import io.circe._, io.circe.parser._

  val doc: Json = parse(dpl).getOrElse(Json.Null)

  val c: HCursor = doc.hcursor

  val typeNode = c.downField("deploy").downField("payment").downField("ModuleBytes").downField("args").downArray.downArray.downN(0). downField("cl_type").as[String]

  println("dsfsdfdsfsdfsdfdsfsdf"+ typeNode.getOrElse(""))

  /*


  import io.circe.DecodingFailure

  io.circe.jawn.decode[DeployResult](dpl)
  match {
    case Left(DecodingFailure(_, history)) => history.reverse.foreach(println)
    case Right(DeployResult) => history.reverse.foreach(println)
  }
*/




  //val deploy = testnetclient.getDeploy("e9d65889f34e5e6f38b24f2402f78e4a0a2cbf8f9176a369fcd61782d88fc717");
  //println(deploy)
 /*
  //Account
  val getstateitemaccount = testnetclient.getStateItem("30cE5146268305AeeFdCC05a5f7bE7aa6dAF187937Eed9BB55Af90e1D49B7956","account-hash-46dE97966cfc2F00C326e654baD000AB7a5E26bEBc316EF4D74715335cF32A88",Seq.empty)
  println(getstateitemaccount)
  //contract
  val getstateitemcontract = testnetclient.getStateItem("30cE5146268305AeeFdCC05a5f7bE7aa6dAF187937Eed9BB55Af90e1D49B7956", "hash-4dd10a0b2a7672e8ec964144634ddabb91504fe50b8461bac23584423318887d")
  println(getstateitemcontract)
  //clvalue
  val getitemClvalue = testnetclient.getStateItem("808ad642fefe6a0d3cadfb151a39aecb37183121ae20565ab32f5c04db20513e", "hh-4dd10a0b2a7672e8ec964144634ddabb91504fe50b8461bac23584423318887d")
  println(getitemClvalue)
*/
  //balance
  val getbalance1 = testnetclient.getBalance("30cE5146268305AeeFdCC05a5f7bE7aa6dAF187937Eed9BB55Af90e1D49B7956",
    URef("uref-9cC68775d07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d067C4D0363-007").get)

  println(getbalance1)

  //dictionaryItem
  val dictionaryItem = testnetclient.getDictionaryItem("8180307A39A8583a4a164154C360FB9Ab9B15A5B626295635A62DFc7A82e66a3",
  "a8261377ef9cf8e741dd6858801c71e38c9322e66355586549b75ab24bdd73f2","uref-F5ea525E6493B41DC3c9b196ab372b6F3f00cA6F1EEf8fe0544e7d044E5480Ba-007")

  println(dictionaryItem)


  //accountinfo
  val accountinfo = testnetclient.getAccountInfo ("017d9aa0b86413d7ff9a9169182c53f0bacaa80d34c211adab007ed4876af17077",
    new HashBlockIdentifier("c4bc7b4a651f5062502841bbd15f2e646e20df4eb406656f7c5ea9952403a086"))
  println(accountinfo)



  //schema
  print(testnetclient.getRpcSchema())

  //Account
  //val getstateitemaccount = testnetclient.getStateItem("30cE5146268305AeeFdCC05a5f7bE7aa6dAF187937Eed9BB55Af90e1D49B7956", "account-hash-46dE97966cfc2F00C326e654baD000AB7a5E26bEBc316EF4D74715335cF32A88", Seq.empty)
  //println(getstateitemaccount)


  //getGlobalstate
  val getGlobalstate = testnetclient.queryGlobalState(new HashBlockIdentifier("10affd579b9404380a85421a53edd4c4540caec070d04e034a41570d26568969")
  ,"deploy-93549a4b0f1f39c1bf21f91dcc84d66dbcf8bee5d8b7e6795077e901a15ea3ec", Seq.empty)

  println(getGlobalstate)


  val pubkey =  CLPublicKey("017d96b9A63ABCB61C870a4f55187A0a7AC24096Bdb5Fc585c12a686a4D892009e")

  val pubkey1 =  null

  println(CirceConverter.toJson[CLPublicKey](pubkey1))

  val json = CirceConverter.toJson(pubkey).get
  println(CirceConverter.toJson(pubkey))

  println(CirceConverter.convertToObj[Option[CLPublicKey]](json).get)


  val json2 =
    """{
      |  "cl_type" : {
      |    "Option" : "String"
      |  },
      |  "bytes" : "010d00000048656c6c6f2c20576f726c6421",
      |  "parsed" : "Hello, World!"
      |}""".stripMargin
  val v0 = CLValue.Option(CLValue.String("Hello, World!"))

  val v1 = CLValue.List(CLValue.String("abc").get,CLValue.String("defg").get , CLValue.String("hijklmnopq").get)

  val v3 = CLValue.Tuple1(CLValue.String("abcef").get)

  val v4 = CLValue.Tuple3(CLValue.PublicKey("01a018bf278f32fdb7b06226071ce399713ace78a28d43a346055060a660ba7aa9").get,CLValue.Option(CLValue.String("abc")).get, CLValue.U512(2).get)
  import com.casper.sdk.types.cltypes.*
  val v5 = CLValue.Ok(CLValue.String("goodresult").get,new CLTypeInfo (CLType.String))


  println("klawiiiiiii " +CirceConverter.toJson(v0).get)// == json)

  println("klawiiiiiii " +CirceConverter.toJson(v1).get)// == json)
  println("klawiiiiiii " +CirceConverter.toJson(v3).get)// == json)
  println("klawiiiiiii " +CirceConverter.toJson(v4).get)// == json)
  println("klawiiiiiii " +CirceConverter.toJson(v5).get)// == json)

  import io.circe._,
  io.circe.syntax._


  val jjj =
    """{
      |  "StoredContractByHash" : {
      |    "hash" : "b348fdd0d0b3f66468687df93141b5924f6bb957d5893c08b60d5a78d0b9a423",
      |    "entry_point" : "entry-point",
      |    "args" : [
      |      [
      |        "bar",
      |        {
      |          "cl_type" : "U8",
      |          "bytes" : "000000000000000000000000",
      |          "parsed" : "12"
      |        }
      |      ],
      |      [
      |        "foo",
      |        {
      |          "cl_type" : "String",
      |          "bytes" : "0700000048656c6c6f2121",
      |          "parsed" : "Hello!!"
      |        }
      |      ]
      |    ]
      |  }
      |}""".stripMargin

  val args1 = Seq(new DeployNamedArg("bar", CLValue.U8(12)), DeployNamedArg("foo", CLValue.String("Hello!!")),DeployNamedArg("faaa", None))
  val storedContractByHash =     new StoredContractByHash(Hash("b348fdd0d0b3f66468687df93141b5924f6bb957d5893c08b60d5a78d0b9a423").get, "entry-point", args1)

  println("zzzzz "+CirceConverter.toJson[StoredContractByHash](storedContractByHash))



  val dddd = Seq(new DeployNamedArg("amount", CLValue.I32(1000)))
  val deployTransfer = new Transfer(dddd)
  println("klawiiiiiii " +CirceConverter.toJson[Transfer](deployTransfer).get)// == json)

  val tt = "scala dvdfg exercises".asJson
  val jsonString: Json = Json.fromString("scala exercises")

  val map: Map[String, Long] = Map(
    "sdfdf" -> 121212122,
    "45454sdfdf" -> 12121212

  )

  val seq = Array.empty[String]

  val jsson= seq.asJson
  println(jsson.asArray)

  val jsonn = map.asJson
  println(jsonn)
 // println(json.as[Map[String, Long]])


  println(JsonConverter.toJson(map))
  */
}
