package com.casper.sdk.rpc

import com.casper.sdk.CasperSdk
import com.casper.sdk.crypto.hash.{Blake2b256, Hash}
import com.casper.sdk.domain.deploy.{Deploy, DeployHeader, DeployNamedArg, ModuleBytes, StoredVersionedContractByName, DeployTransfer}
import com.casper.sdk.serialization.domain.deploy.DeployHeaderByteSerializer
import com.casper.sdk.serialization.domain.deploy.DeployExecutableByteSerializer

import com.casper.sdk.types.cltypes.{CLPublicKey, CLValue}
import com.casper.sdk.util.{HexUtils, JsonConverter}

import com.casper.sdk.util.implicits.idInstance
import scala.collection.mutable.ArrayBuilder

object TestSecp256k1 extends App{


  //rpc client
  val client = new CasperSdk("http://65.21.227.180:7777/rpc")


  //keys
  val keys = com.casper.sdk.crypto.KeyPair. loadFromPem(getClass.getResource("/crypto/secp256k1/put_deploy_secret_key.pem").getPath)


  //header
  val header = new DeployHeader(
    CLPublicKey("02038debf99b9850210d5e5a3c3748db03cc31fc236197010931909350c32acf1689").get,
    System.currentTimeMillis(),
    5400000L,
    1,
    None,
    Seq.empty,
    "casper-test"
  )



  //payment component

  val arg0 = new DeployNamedArg("amount", CLValue.U512(5000000000L))
  val payment = new ModuleBytes("".getBytes() , Seq(Seq(arg0)))

  //create session component
  val arg1 = new DeployNamedArg("amount", CLValue.U512(5000000000L))
  val arg01 = new DeployNamedArg("target",
    CLValue.PublicKey("017d9aa0b86413d7ff9a9169182c53f0bacaa80d34c211adab007ed4876af17077")
  )

  val arg02 = new DeployNamedArg("id", CLValue.Option(CLValue.U64(1L))
  )


  val session = new DeployTransfer(Seq(Seq(arg1, arg01, arg02)))


 //create unsigned deploy
  val deploy = Deploy.createUnsignedDeploy(header, payment, session)

  //sign it
  val signedDeploy = Deploy.signDeploy(deploy, keys)


  //json call (use with postman)
  println(JsonConverter.toJson(signedDeploy))

}
