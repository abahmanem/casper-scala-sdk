package com.casper.sdk.rpc

import com.casper.sdk.domain
import com.casper.sdk.CasperSdk
import com.casper.sdk.crypto.KeyPair
import com.casper.sdk.crypto.hash.Blake2b256
import com.casper.sdk.domain.{EraSummary, Peer, deploy}
import com.casper.sdk.domain._
import com.casper.sdk.domain.deploy.{Deploy, DeployExecutable, DeployNamedArg, Hash, ModuleBytes, StoredContractByHash, StoredVersionedContractByHash, StoredVersionedContractByName}
import com.casper.sdk.types.cltypes.{CLResultTypeInfo,CLTuple1TypeInfo,CLTuple2TypeInfo,CLTuple3TypeInfo,CLListTypeInfo,AccessRight, AccountHash, CLByteArrayTypeInfo, CLOptionTypeInfo, CLPublicKey, CLType, CLTypeInfo, CLValue, KeyAlgorithm, Signature, URef}
import com.casper.sdk.util.{ByteUtils, HexUtils, JsonConverter, TimeUtil}
import com.casper.sdk.util.implicits.idInstance
import org.bouncycastle.crypto.KeyGenerationParameters
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters
import scodec.bits.ByteVector
import scodec.bits.hex
import org.scalactic.Prettifier.default

import java.io.{File, FileWriter, StringWriter}
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import math.BigInt.int2bigInt
import scala.collection.mutable.ArrayBuilder
import java.nio
import scala.io.Source
import java.net.URL
import java.security.SecureRandom
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.PEMWriter
import com.casper.sdk.serialization.domain.deploy.{DeployApprovalByteSerializer, DeployExecutableByteSerializer, DeployHeaderByteSerializer}
import com.casper.sdk.domain.deploy._
import com.casper.sdk.json.serialize.TimeStampSerializer
import com.casper.sdk.serialization.cltypes.CLValueByteSerializer

import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PublicKey
import java.security.SecureRandom
import java.security.spec.ECGenParameterSpec
import java.security.{KeyFactory, KeyPair, KeyPairGenerator, PrivateKey, PublicKey, Signature}
import java.time.{Instant, ZoneId}
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

object TestnetTester  extends  App {





  val header = new DeployHeader(
    new CLPublicKey("017d9aa0b86413d7ff9a9169182c53f0bacaa80d34c211adab007ed4876af17077"),
    TimeUtil.ToEpochMs("2022-01-20T16:39:24.072Z"),
    1800000L,
    1,
    null,
    Seq(new Hash("0101010101010101010101010101010101010101010101010101010101010101")),
      "casper-test"
     )

  println(JsonConverter.toJson(header))

  val sss = new DeployHeaderByteSerializer()
  println(HexUtils.toHex(sss.toBytes(header)))


  val arg0 = new DeployNamedArg("quantity",CLValue.I32(1000))
  val payment = new StoredContractByName(
  "casper-example",
  "example-entry-point", Seq(Seq(arg0)))

  val arg1 = new DeployNamedArg("amount",CLValue.U512(25000000000L))
  val arg01 = new DeployNamedArg("target",CLValue.ByteArray(HexUtils.fromHex("0101010101010101010101010101010101010101010101010101010101010101")))
  val session =  new DeployTransfer(Seq(Seq(arg1,arg01)))
  //println(JsonConverter.toJson(header))






  val pair = com.casper.sdk.crypto.KeyPair.loadFromPem("/Users/p35862/testnet.pem")

  val serializer = DeployExecutableByteSerializer()
  val builder = new ArrayBuilder.ofByte
//  builder.addAll(serializer.toBytes(payment)).addAll(serializer.toBytes(session))
  println("SSSSSSS  "+HexUtils.toHex(Deploy.deployBodyHash(payment,session)))



  val deploy = Deploy.createUnsignedDeploy(header,payment,session)
  println("//////////////////////////////////////////////////////////////////////////////////////////////////////////////")
  println(HexUtils.toHex(Blake2b256.hash(Deploy.deployHeaderHash(deploy.header))))
  val signedDeploy = Deploy.signDeploy(deploy,pair)
  println("//////////////////////////////////////////////////////////////////////////////////////////////////////////////")
  println(JsonConverter.toJson(signedDeploy))




/*
  val hexSECP256K1 = "0203e7d5b66b2fd0f66fb0efcceecb673b3762595b30ae1cac48ae8f09d34c952ee4"
  val keyESECP256K1 = new CLPublicKey(hexSECP256K1)
  val header  = new DeployHeader(keyESECP256K1,"1800000","1800000",1,null,null,"casper-test")
  val paiment = new ModuleBytes()
  val session = new StoredContractByHash()
  val deploy = Deploy.createUnsignedDeploy()
*/


/*
  import org.bouncycastle.jce.provider.BouncyCastleProvider
  import java.security.Security
//  Security.addProvider(new BouncyCastleProvider())
  //Security.addProvider(new BouncyCastleProvider)
  pemParser.close


  val provider = new BouncyCastleProvider()
  val converter = new JcaPEMKeyConverter().setProvider(provider);

  //val converter = new JcaPEMKeyConverter().setProvider("BC")
  if (obj.isInstanceOf[SubjectPublicKeyInfo]) {
    val bytes = obj.asInstanceOf[SubjectPublicKeyInfo].getEncoded()
    val algo = converter.getPublicKey(obj.asInstanceOf[SubjectPublicKeyInfo]).getAlgorithm

    algo match {
      case "Ed25519" => new CLPublicKey(bytes, KeyAlgorithm.ED25519)
      case "ECDSA" => new CLPublicKey(bytes, KeyAlgorithm.SECP256K1)
    }
  }
  else throw new IllegalArgumentException("not a public key file PEM")


*/


/*
 val client = new CasperSdk("http://65.108.1.10:7777/rpc")

  val deploy = client.getDeploy("5545207665f6837F44a6BCC274319280B73a6f0997F957A993e60f878A736678")



 val fileWriter = new FileWriter(new File("auction.json"))
 fileWriter.write(JsonConverter.toJson(client.getAuctionInfo("9F2D38d6b6d139DAA70fEF85FE7f1907A1ce055ca87e260995E4B84D161d42E5")))
 fileWriter.close()
 */


/*
  println("sd "+deploy.payment.getClass)
  println("de "+deploy)

  val str = """[["amount",{"cl_type":"U512","bytes":"04005670E3","parsed":"3815790080"}]] """

 val l= JsonConverter.fromJson[Seq[Seq[DeployNamedArg]]](str)


  println(l)


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

 //val b = BigInt.apply("123456789101112131415")// .int2bigInt(b)
 //var bytes = b.toByteArray

val b = CLValue.I64(-4700)//

println(HexUtils.toHex(b.bytes))

val str = """ {
            |    "hash": "01da3c604f71e0e7df83ff1ab4ef15bb04de64ca02e3d2b78de6950e8b5ee187",
            |    "header": {
            |        "account": "01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900c",
            |        "timestamp": "1605573564072",
            |        "ttl": "3600000",
            |        "gas_price": 1,
            |        "body_hash": "4811966d37fe5674a8af4001884ea0d9042d1c06668da0c963769c3a01ebd08f",
            |        "dependencies": [
            |            "0101010101010101010101010101010101010101010101010101010101010101"
            |        ],
            |        "chain_name": "casper-example"
            |    },
            |    "payment": {
            |        "StoredContractByName": {
            |        "name": "casper-example",
            |        "entry_point": "example-entry-point",
            |        "args": [
            |            [
            |                "quantity",
            |                {
            |                    "cl_type": "I32",
            |                    "bytes": "e8030000",
            |                    "parsed": 1000
            |                }
            |            ]
            |        ]
            |        }
            |    },
            |    "session": {
            |        "Transfer": {
            |        "args": [
            |            [
            |                "amount",
            |                {
            |                    "cl_type": "I32",
            |                    "bytes": "e8030000",
            |                    "parsed": 1000
            |                }
            |            ]
            |        ]
            |        }
            |    },
            |    "approvals": [
            |        {
            |            "signer": "01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900c",
            |            "signature": "012dbf03817a51794a8e19e0724884075e6d1fbec326b766ecfa6658b41f81290da85e23b24e88b1c8d9761185c961daee1adab0649912a6477bcd2e69bd91bd08"
            |        }
            |    ]
            |}""".stripMargin




val str1= """{
            |        "StoredContractByName": {
            |        "name": "casper-example",
            |        "entry_point": "example-entry-point",
            |        "args": [
            |            [
            |                "quantity",
            |                {
            |                    "cl_type": "I32",
            |                    "bytes": "e8030000",
            |                    "parsed": 1000
            |                }
            |            ]
            |        ]
            |        }
            |    }""".stripMargin



val deploy = JsonConverter.fromJson[Deploy](str)
  println(deploy)
val deployApprovalByteSerializer = new DeployByteSerializer()

 // deployApprovalByteSerializer.toBytes(deploy)

println(HexUtils.toHex(deployApprovalByteSerializer.toBytes(deploy)))

  //println("B-"+HexUtils.toHex(CLValue.String(storedContractByName.entry_point).bytes))


  println(TimeUtil.ToEpochMs("2020-11-17T00:39:24.072Z"))
  import com.casper.sdk.serialization.cltypes.CLValueByteSerializer

  val cLValueByteSerializer = new CLValueByteSerializer()

  val clValue = CLValue.List()

  println(HexUtils.toHex(clValue.bytes))

  var bytes = cLValueByteSerializer.toBytes(clValue)

  println(HexUtils.toHex(bytes))

//  assert("08000000000000000000000003"== HexUtils.toHex(bytes))


  val serializer = new DeployApprovalByteSerializer()
  val signer= new CLPublicKey("01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900c")
  val signature= new Signature("012dbf03817a51794a8e19e0724884075e6d1fbec326b766ecfa6658b41f81290da85e23b24e88b1c8d9761185c961daee1adab0649912a6477bcd2e69bd91bd08")

  val approval = new DeployApproval(signer,signature)

  bytes = serializer.toBytes(approval)

  println(HexUtils.toHex(bytes))

  val ser = new CLPublicKeySerializer()
  val pbukey= new CLPublicKey("01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900c")

  bytes = ser.toBytes(pbukey)

  val ipaddr = Array[Byte](192.toByte, 168.toByte, 1, 9)


  val kk = Array[Byte](1, -39, -65, 33, 72, 116, -118, -123, -56, -99, -91, -86, -40, -18, 11, 15, -62, -47, 5, -3, 57, -44, 26, 76, 121, 101, 54, 53, 79, 10, -30, -112, 12)


  val srr = new DeployNamedArgByteSerializer()

  var arg = new DeployNamedArg("test",CLValue.String("Hello, World!"))


  println(HexUtils.toHex(srr.toBytes(arg)))


  val builder = new ArrayBuilder.ofByte
  builder.addAll(CLValue.U32("test".getBytes().length).bytes)
    .addAll("test".getBytes())
   // .addAll(new CLValueByteSerializer().toBytes(CLValue.String("Hello, World!")))


  builder .addAll(new CLValueByteSerializer().toBytes(new URef("uref-9cC68775d07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d067C4D0363-007")))
  var xxx = new DeployNamedArg("test",new URef("uref-9cC68775d07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d067C4D0363-007"))
  assert(srr.toBytes(xxx).sameElements(builder.result()))

*/
}
