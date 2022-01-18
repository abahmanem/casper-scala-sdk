package com.casper.sdk.json.deserialize.serialize

import com.casper.sdk.domain.deploy._
import com.casper.sdk.types.cltypes.{CLPublicKey, CLValue}
import com.casper.sdk.util.JsonConverter
import org.scalatest.funsuite.AnyFunSuite
/**
 * DeployExecutable Custom Json serializer 
 */
class DeployExecutableSerializerTest extends AnyFunSuite {


  test("DeployExecutableSerializerTest with StoredVersionedContractByName"){
    val json = """{
                 |  "StoredVersionedContractByName" : {
                 |    "name" : "casper-test",
                 |    "version" : Some(1425474),
                 |    "entry_point" : "entry-point",
                 |    "args" : [
                 |      [
                 |        "bar",
                 |        {
                 |          "cl_type" : "U512",
                 |          "bytes" : "057c4909b505",
                 |          "parsed" : 24512121212
                 |        }
                 |      ],
                 |      [
                 |        "foo",
                 |        {
                 |          "cl_type" : "U64",
                 |          "bytes" : "91eaffffffffffff",
                 |          "parsed" : -5487
                 |        }
                 |      ]
                 |    ]
                 |  }
                 |}""".stripMargin

    val arg1 = new DeployNamedArg("bar", CLValue.U512(BigInt.apply("24512121212")))
    val arg2 = new DeployNamedArg("foo", CLValue.U64(-5487))
    val storedVersionedContractByName = new StoredVersionedContractByName ("casper-test", Some(1425474), "entry-point",Seq(Seq(arg1,arg2)))
    assert(JsonConverter.toJson(storedVersionedContractByName)==json)
  }

}