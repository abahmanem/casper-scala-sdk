package com.casper.sdk.rpc

import com.casper.sdk.crypto.KeyPair
import com.casper.sdk.crypto.hash.{Blake2b256, Hash}
import com.casper.sdk.{CasperSdk, domain, util}
import com.casper.sdk.domain.deploy._
import com.casper.sdk.domain.{EraSummary, Peer, deploy}
import com.casper.sdk.json.serialize.TimeStampSerializer
import com.casper.sdk.rpc.exceptions.RPCException
import com.casper.sdk.serialization.cltypes.CLValueByteSerializer
import com.casper.sdk.serialization.domain.deploy.{DeployApprovalByteSerializer, DeployExecutableByteSerializer, DeployHeaderByteSerializer}
import com.casper.sdk.types.cltypes._
import com.casper.sdk.util.{ByteUtils, HexUtils, JsonConverter, TimeUtil}
import org.bouncycastle.crypto.KeyGenerationParameters
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.PEMWriter
import org.scalactic.Prettifier.default

import java.io.{File, FileWriter, StringWriter}
import java.math.BigInteger
import java.net.URL
import java.nio
import java.nio.charset.StandardCharsets
import java.security.spec.ECGenParameterSpec
import java.security._
import java.time.format.DateTimeFormatter
import java.time.{Instant, ZoneId}
import java.util.regex.Pattern
import scala.collection.mutable.ArrayBuilder
import scala.io.Source
import scala.math.BigInt.int2bigInt
import org.scalatest.TryValues
import com.casper.sdk.domain._
import com.casper.sdk.domain.deploy._
import com.casper.sdk.rpc.exceptions._
import com.casper.sdk.types.cltypes._
import com.casper.sdk.util.HexUtils
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.flatspec._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.TryValues
import org.scalatest._
import matchers.should._
import org.apache.commons.codec.binary.Hex
import scala.util.{Try, Success, Failure}

object TestnetTester extends AnyFlatSpec with App with Matchers with TryValues {



  val str1 = """ {"ModuleBytes" : {
               |          "module_bytes" : "",
               |          "args" : [
               |            [
               |              "amount",
               |              {
               |                "cl_type" : "U512",
               |                "parsed" : "100000000",
               |                "bytes" : "0400e1f505"
               |              }
               |            ]
               |          ]
               |        }
               |      }}""".stripMargin


  val md = JsonConverter.fromJson[ModuleBytes](str1)

  val str2= """{
              |        "Transfer" : {
              |          "args" : [
              |            [
              |              "amount",
              |              {
              |                "cl_type" : "U512",
              |                "parsed" : "3000000000",
              |                "bytes" : "04005ed0b2"
              |              }
              |            ],
              |            [
              |              "target",
              |              {
              |                "cl_type" : "PublicKey",
              |                "parsed" : "02021172744b5e6bdc83a591b75765712e068e5d40a3be8ae360274fb26503b4ad38",
              |                "bytes" : "02021172744b5e6bdc83a591b75765712e068e5d40a3be8ae360274fb26503b4ad38"
              |              }
              |            ],
              |            [
              |              "id",
              |              {
              |                "cl_type" : {
              |                  "Option" : "U64"
              |                },
              |                "bytes" : "010000000000000000",
              |                "parsed" : 0
              |              }
              |            ]
              |          ]
              |        }
              |
              |} """.stripMargin

  val se = JsonConverter.fromJson[DeployTransfer](str2)


  val seri = new DeployExecutableByteSerializer

  val builder = new ArrayBuilder.ofByte



  val client = new CasperSdk("http://65.21.227.180:7777/rpc")


  //Header
  val header = new DeployHeader(
    CLPublicKey("0168688cd4db3bd37efd84b15dc5a1867465df4c429e17fe22954fea88f5b4e1fe"),
    Some(System.currentTimeMillis()),
    Some(5400000L),
    1,
    None,
    Seq.empty,
    "casper-test"
  )

  // payment
  val arg0 = new DeployNamedArg("amount", CLValue.U512(1000000000L))
  val payment = new ModuleBytes("".getBytes(), Seq(Seq(arg0)))

  //session
  val arg1 = new DeployNamedArg("amount", CLValue.U512(3000000000L))
  val arg01 = new DeployNamedArg("target", CLValue.PublicKey("02021172744b5e6bdc83a591b75765712e068e5d40a3be8ae360274fb26503b4ad38"))
  val arg02 = new DeployNamedArg("id", CLValue.Option(CLValue.U64(0)))
  val session = new DeployTransfer(Seq(Seq(arg1, arg01, arg02)))

  val keyPair = com.casper.sdk.crypto.KeyPair.loadFromPem(getClass.getResource("/crypto/sign_put_deploys_secret.pem").getPath)
  val deploy = Deploy.createUnsignedDeploy(header, payment, session)
  val signedDeploy = Deploy.signDeploy(deploy, keyPair.get)

  //val hash = client.putDeploy(signedDeploy.get)


  //assert(caught.getMessage == "An error occured when invoking RPC method: chain_get_block with params: " +
  // "ArraySeq(Map(Height -> 8745812)). RPC error code: -32001 , RPC error message: block not known")

  /*

    val client = new CasperSdk("http://65.21.227.180:7777/rpc")
    val dp = client.getDeploy("0fe0adccf645e99b9b58493c843516cd354b189e1c3efe62c4f2768716a41932")

    val header = new DeployHeader(
      CLPublicKey("017d9aa0b86413d7ff9a9169182c53f0bacaa80d34c211adab007ed4876af17077").get,
      System.currentTimeMillis(),
      5400000L,
      1,
      None,
      Seq.empty,
      "casper-test"
    )


    val sss = new DeployHeaderByteSerializer()


    val arg0 = new DeployNamedArg("amount", CLValue.U512(5000000000L))
    val payment = new ModuleBytes(
      "".getBytes()
      , Seq(Seq(arg0)))

    val arg1 = new DeployNamedArg("amount", CLValue.U512(5000000000L))
    val arg01 = new DeployNamedArg("target",
      CLValue.PublicKey("014d8c494ae85bda4288d0ed02c6bb180ec84efd3d7af0ed6ab9092d8757441427")
    )

    val arg02 = new DeployNamedArg("id",
      CLValue.Option(CLValue.U64(999L))
    )


    val session = new DeployTransfer(Seq(Seq(arg1, arg01, arg02)))


    val session1 = new StoredVersionedContractByName("test", Some(12542), "entry_point", Seq(Seq(arg1, arg01, arg02)))

    val pair = com.casper.sdk.crypto.KeyPair.loadFromPem("/Users/p35862/testnet.pem")

    val serializer = DeployExecutableByteSerializer()
    val builder = new ArrayBuilder.ofByte
    //  builder.addAll(serializer.toBytes(payment)).addAll(serializer.toBytes(session))
    val mm = com.casper.sdk.crypto.KeyPair.loadFromPem("/Users/p35862/pppp.pem")


    val deploy = Deploy.createUnsignedDeploy(header, payment, session)
    val signedDeploy = Deploy.signDeploy(deploy, mm)
  */
}
