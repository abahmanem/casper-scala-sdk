
### Serialization
Casper provides a custom implementation to serialize data structures used by the Casper node to their byte representation.
More details on this implementation can be found here :
[serialization-standard](https://caspernetwork.readthedocs.io/en/latest/implementation/serialization-standard.html?highlight=serialization)

### Examples

#### CLType Serialization

- String

```scala
val clValue = CLValue.String("Hello, World!")
assert("0d00000048656c6c6f2c20576f726c6421" == HexUtils.toHex(clValue.bytes))

```

- U32

```scala
  val bytes = cLValueByteSerializer.toBytes((CLValue.U32(1024)))
    assert("040000000004000004" == HexUtils.toHex(bytes))
```

- Option

```scala
    val clValue = CLValue.Option(CLValue.U32(10))
    assert("010a000000" == HexUtils.toHex(clValue.bytes))
```


#### CLValue Serialization

- CLValue with U512 CLType

```scala
     val bytes = cLValueByteSerializer.toBytes((CLValue.U512(BigInt.apply("123456789101112131415"))))
     assert("0a0000000957ff1ada959f4eb10608" == HexUtils.toHex(bytes))
```

- CLValue with List of CLType

```scala
    val bytes = cLValueByteSerializer.toBytes((CLValue.List(CLValue.U32(1), CLValue.U32(2), CLValue.U32(3))))
    assert("100000000300000001000000020000000300000004" == HexUtils.toHex(bytes))
```

- CLValue with Tuple2  CLType

```scala
      val clValue = CLValue.Tuple2(CLValue.U32(1), CLValue.String("Hello, World!"))
      assert("010000000d00000048656c6c6f2c20576f726c6421" == HexUtils.toHex(clValue.bytes))
```

More examples on CLType and CLValue serialization can be found in this test file :

[cltypes/CLValueByteSerializerTest.scala](https://github.com/caspercommunityio/casper-scala-sdk/blob/master/src/test/scala/com/casper/sdk/serialization/cltypes/CLValueByteSerializerTest.scala)


### Serialization of DeployExecutable objects

```scala
val serializer = new DeployExecutableByteSerializer
```

- ModuleBytes 

```scala
    val args: DeployNamedArg = new DeployNamedArg("payment", CLValue.U512(BigInt.apply("9999999999")))
    val ModuleBytes = new ModuleBytes(Array.fill[Byte](75)(1), Seq(Seq(args)))
    assert("004b00000001010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101000000070000007061796d656e740600000005ffe30b540208" == HexUtils.toHex(serializer.toBytes(ModuleBytes)))

```

- StoredContractByHash  

```scala
    val args: DeployNamedArg = new DeployNamedArg("quantity", CLValue.I32(1000))
    val storedContractByHash = new StoredContractByHash("c4c411864f7b717c27839e56f6f1ebe5da3f35ec0043f437324325d65a22afa4", "pclphXwfYmCmdITj8hnh", Seq(Seq(args)))
    assert("01c4c411864f7b717c27839e56f6f1ebe5da3f35ec0043f437324325d65a22afa41400000070636c7068587766596d436d6449546a38686e6801000000080000007175616e7469747904000000e803000001" == HexUtils.toHex(serializer.toBytes(storedContractByHash)))
  
```

- StoredContractByName  

```scala
    val args: DeployNamedArg = new DeployNamedArg("quantity", CLValue.I32(1000))
    val storedContractByName = new StoredContractByName("casper-example", "example-entry-point", Seq(Seq(args)))
    assert("020e0000006361737065722d6578616d706c65130000006578616d706c652d656e7472792d706f696e7401000000080000007175616e7469747904000000e803000001" == HexUtils.toHex(serializer.toBytes(storedContractByName)))

```

- StoredVersionedContractByHash 

```scala
    val args: DeployNamedArg = new DeployNamedArg("test", CLValue.String("Hello, World!"))
    val storedVersionedContractByHash = new StoredVersionedContractByHash("b348fdd0d0b3f66468687df93141b5924f6bb957d5893c08b60d5a78d0b9a423", None, "PsLz5c7JsqT8BK8ll0kF", Seq(Seq(args)))
    assert("03b348fdd0d0b3f66468687df93141b5924f6bb957d5893c08b60d5a78d0b9a42300010000000400000074657374110000000d00000048656c6c6f2c20576f726c64210a" == HexUtils.toHex(serializer.toBytes(storedVersionedContractByHash)))
  
```

- DeployTransfer   

```scala
   val args: DeployNamedArg = new DeployNamedArg("amount", CLValue.I32(1000))
   val deployTransfer = new DeployTransfer(Seq(Seq(args)))
   assert("050100000006000000616d6f756e7404000000e803000001" == HexUtils.toHex(serializer.toBytes(deployTransfer)))
```

- StoredVersionedContractByName    

```scala
 val args: DeployNamedArg = new DeployNamedArg("test", CLValue.String("Hello, World!"))
    val storedVersionedContractByName = new StoredVersionedContractByName("test-contract", None, "PsLz5c7JsqT8BK8ll0kF", Seq(Seq(args)))
    assert("040d000000746573742d636f6e747261637400010000000400000074657374110000000d00000048656c6c6f2c20576f726c64210a" == HexUtils.toHex(serializer.toBytes(storedVersionedContractByName)))
  
```

### Serialization of Deploy  objects

```scala
val serializer = new DeployByteSerializer()
val serializedDeploy = "01d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900ca856a4d37501000080ee36000000000001000000000000004811966d37fe5674a8af4001884ea0d9042d1c06668da0c963769c3a01ebd08f0100000001010101010101010101010101010101010101010101010101010101010101010e0000006361737065722d6578616d706c6501da3c604f71e0e7df83ff1ab4ef15bb04de64ca02e3d2b78de6950e8b5ee187020e0000006361737065722d6578616d706c65130000006578616d706c652d656e7472792d706f696e7401000000080000007175616e7469747904000000e803000001050100000006000000616d6f756e7404000000e8030000010100000001d9bf2148748a85c89da5aad8ee0b0fc2d105fd39d41a4c796536354f0ae2900c012dbf03817a51794a8e19e0724884075e6d1fbec326b766ecfa6658b41f81290da85e23b24e88b1c8d9761185c961daee1adab0649912a6477bcd2e69bd91bd08"
    val jsondeploy = """ {
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

    assert(serializedDeploy== HexUtils.toHex(serializer.toBytes(JsonConverter.fromJson[Deploy](jsondeploy))))
```