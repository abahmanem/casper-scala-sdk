package com.casper.sdk.json.serialize

import com.casper.sdk.crypto.hash.Hash
import com.casper.sdk.domain.deploy.*
import com.casper.sdk.types.cltypes.{CLPublicKey, CLValue}
import com.casper.sdk.util.{CirceConverter}
import org.scalatest.funsuite.AnyFunSuite
/**
 * DeployExecutable Custom Json serializer 
 */
class DeployExecutableSerializerTest extends AnyFunSuite {


  test("DeployExecutableSerializerTest with StoredContractByHash") {
    val json : String=
      """{
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
        |}""".stripMargin

    val args1 = Seq(new DeployNamedArg("bar", CLValue.U8(12)), DeployNamedArg("foo", CLValue.String("Hello!!")))
    val storedContractByHash = new StoredContractByHash(Hash("b348fdd0d0b3f66468687df93141b5924f6bb957d5893c08b60d5a78d0b9a423").get, "entry-point", args1)
    assert(CirceConverter.toJson(storedContractByHash).get == json.split("\\s+").mkString)
  }


  test("DeployExecutableSerializerTest with StoredVersionedContractByHash") {
    val json =
      """{
        |    "hash" : "b348fdd0d0b3f66468687df93141b5924f6bb957d5893c08b60d5a78d0b9a423",
        |    "version" : 1,
        |    "entry_point" : "entry-point",
        |    "args" : [
        |      [
        |        "bar",
        |        {
        |          "cl_type" : "U512",
        |          "bytes" : "03fc5d20",
        |          "parsed" : "2121212"
        |        }
        |      ],
        |      [
        |        "foo",
        |        {
        |          "cl_type" : {
        |            "ByteArray" : 4
        |          },
        |          "bytes" : "74657374",
        |          "parsed" : "74657374"
        |        }
        |      ]
        |    ]
        |}""".stripMargin


    val arg1 = new DeployNamedArg("bar", CLValue.U512(BigInt.apply("2121212")))
    val arg2 = new DeployNamedArg("foo", CLValue.ByteArray("test".getBytes))
    val args1 = Seq(arg1, arg2)
    val storedVersionedContractByHash = new StoredVersionedContractByHash(Hash("b348fdd0d0b3f66468687df93141b5924f6bb957d5893c08b60d5a78d0b9a423").get, 1, "entry-point", args1)
    assert(CirceConverter.toJson(storedVersionedContractByHash).get == json.split("\\s+").mkString)
  }


  test("DeployExecutableSerializerTest with StoredVersionedContractByName"){
    val json = """{
                 |    "name" : "casper-test",
                 |    "version" : 1425474,
                 |    "entry_point" : "entry-point",
                 |    "args" : [
                 |      [
                 |        "bar",
                 |        {
                 |          "cl_type" : "U512",
                 |          "bytes" : "057c4909b505",
                 |          "parsed" : "24512121212"
                 |        }
                 |      ],
                 |      [
                 |        "foo",
                 |        {
                 |          "cl_type" : "U64",
                 |          "bytes" : "91eaffffffffffff",
                 |          "parsed" : "-5487"
                 |        }
                 |      ]
                 |    ]
                 |}""".stripMargin

    val arg1 = new DeployNamedArg("bar", CLValue.U512(BigInt.apply("24512121212")))
    val arg2 = new DeployNamedArg("foo", CLValue.U64(-5487))
    val storedVersionedContractByName = new StoredVersionedContractByName ("casper-test", 1425474, "entry-point",Seq(arg1,arg2))
    assert(CirceConverter.toJson(storedVersionedContractByName).get==json.split("\\s+").mkString)
  }

  test("DeployExecutableSerializerTest with StoredContractByName"){
    val json = """{
                 |    "name" : "casper-test",
                 |    "entry_point" : "entry-point",
                 |    "args" : [
                 |      [
                 |        "bar",
                 |        {
                 |          "cl_type" : "Unit",
                 |          "bytes" : "",
                 |          "parsed" : null
                 |        }
                 |      ],
                 |      [
                 |        "foo",
                 |        {
                 |          "cl_type" : "U512",
                 |          "bytes" : "03f12a16",
                 |          "parsed" : "1452785"
                 |        }
                 |      ]
                 |    ]
                 |}""".stripMargin

    val args1 =  Seq(new DeployNamedArg("bar", CLValue.Unit()),new DeployNamedArg("foo", CLValue.U512(1452785) ))
    val StoredContractByName = new StoredContractByName("casper-test",  "entry-point",args1)
    assert(CirceConverter.toJson(StoredContractByName).get==json.split("\\s+").mkString)
  }


  test("DeployExecutableSerializerTest with Transfer"){
    val json = """{
                 |  "args" : [
                 |      [
                 |        "amount",
                 |        {
                 |          "cl_type" : "I32",
                 |          "bytes" : "e8030000",
                 |          "parsed" : "1000"
                 |        }
                 |      ]
                 |    ]
                 |}""".stripMargin

    val args =  Seq(new DeployNamedArg("amount", CLValue.I32(1000) ))
    val deployTransfer = new Transfer(args)
    assert(CirceConverter.toJson(deployTransfer).get==json.split("\\s+").mkString)
  }

}