package com.casper.sdk.json.deserialize
import com.casper.sdk.domain.deploy.{DeployExecutable, Transfer,ModuleBytes, StoredContractByName, StoredVersionedContractByHash, StoredVersionedContractByName}
import com.casper.sdk.util.CirceConverter
import org.scalatest.funsuite.AnyFunSuite

import scala.io.Source

/**
 * DeployExecutableDeserializerTest
 */

class DeployExecutableDeserializerTest extends AnyFunSuite {

  
  test("DeployExecutableDeserializer test ModuleBytes ") {

    val str = """{"ModuleBytes":{"module_bytes":"","args":[["amount",{"cl_type":"U512","bytes":"04005670E3","parsed":"3815790080"}]]}}"""
    val modulesBytes = CirceConverter.convertToObj[DeployExecutable](str).get
    assert(modulesBytes.getClass.getSimpleName=="ModuleBytes")
  }


  test("DeployExecutableDeserializer test StoredContractByName ") {
    val str ="""{"StoredContractByName": {
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
           |} """.stripMargin
    val storedContractByName = CirceConverter.convertToObj[DeployExecutable](str).get
    assert(storedContractByName.getClass.getSimpleName=="StoredContractByName")

  }

  test("DeployExecutableDeserializer test StoredVersionedContractByHash ") {
    val str ="""{"StoredVersionedContractByHash": {
               |        "hash":"c4c411864f7b717c27839e56f6f1ebe5da3f35ec0043f437324325d65a22afa4",
               |        "version": 1,
               |        "entry_point": "pclphXwfYmCmdITj8hnh",
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
               |} """.stripMargin
    val storedVersionedContractByHash = CirceConverter.convertToObj[DeployExecutable](str).get
    assert(storedVersionedContractByHash.getClass.getSimpleName=="StoredVersionedContractByHash")
  }
  test("DeployExecutableDeserializer test StoredVersionedContractByName ") {
    val str ="""{"StoredVersionedContractByName": {
               |        "name": "lWJWKdZUEudSakJzw1tn",
               |        "version": "1632552656",
               |        "entry_point": "S1cXRT3E1jyFlWBAIVQ8",
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
               |} """.stripMargin
    val storedVersionedContractByName = CirceConverter.convertToObj[DeployExecutable](str).get
    assert(storedVersionedContractByName.getClass.getSimpleName=="StoredVersionedContractByName")
  }

  test("DeployExecutableDeserializer test Transfer ") {

    val str = """{
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
                |    }""".stripMargin
    val transfer = CirceConverter.convertToObj[DeployExecutable](str).get
    assert(transfer.getClass.getSimpleName=="Transfer")
  }
}
