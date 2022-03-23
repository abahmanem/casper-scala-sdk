package com.casper.sdk.crypto

import com.casper.sdk.crypto.util.SECP256K1
import com.casper.sdk.domain.deploy._
import com.casper.sdk.types.cltypes.{CLPublicKey, CLValue}
import com.casper.sdk.util.{HexUtils, JsonConverter}

object TestSecp256k1 extends App {


  //rpc client
  val hash = HexUtils.fromHex("a6123f83c46b3df729487cf71294c8a99e9e47d2c0d3400a6c09803fdb278606")

  val key = CLPublicKey("0203b6f49ad0594b8e1c5116b41d230084f80e0f1941f63013315d738d1a0df0993b").get

  val sig = HexUtils.fromHex("029b97209f36e4765fcaec026e717e04cf0821d8709dede4a776691733b11d98df6d7745c16621eacec7459462da98ff525684028e8a63056c4e49e879b1b93831")



  //keys
  val keys = com.casper.sdk.crypto.KeyPair.loadFromPem(getClass.getResource("/crypto/secp256k1/put_deploy_secret_key.pem").getPath)

  //keys
  //


  //header
  val header = new DeployHeader(
    CLPublicKey("02038debf99b9850210d5e5a3c3748db03cc31fc236197010931909350c32acf1689"),
    Option(System.currentTimeMillis()), Option(1800000L),

    1,
    None,
    Seq.empty,
    "casper-test"
  )



  //payment component

  val arg0 = new DeployNamedArg("amount", CLValue.U512(5000000000L))
  val payment = new ModuleBytes("".getBytes(), Seq(Seq(arg0)))

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
  val signedDeploy = Deploy.signDeploy(deploy, keys.get)



}
