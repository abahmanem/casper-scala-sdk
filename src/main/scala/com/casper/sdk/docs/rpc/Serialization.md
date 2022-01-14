
### Serialization
Casper provides a custom implementation to serialize data structures used by the Casper node to their byte representation
This custom implementation is described in detail here :

[serialization-standard](https://caspernetwork.readthedocs.io/en/latest/implementation/serialization-standard.html?highlight=serialization)

### Example

* CLType Serialization  example :

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


* CLValue Serialization

- CLValue with U512 CLType

```scala
     val bytes = cLValueByteSerializer.toBytes((CLValue.U512(BigInt.apply("123456789101112131415"))))
     assert("0a0000000957ff1ada959f4eb10608" == HexUtils.toHex(bytes))
```

- CLValue of List of CLType

```scala
    val bytes = cLValueByteSerializer.toBytes((CLValue.List(CLValue.U32(1), CLValue.U32(2), CLValue.U32(3))))
    assert("100000000300000001000000020000000300000004" == HexUtils.toHex(bytes))
```

- CLValue with Tuple2  CLType

```scala
      val clValue = CLValue.Tuple2(CLValue.U32(1), CLValue.String("Hello, World!"))
      assert("010000000d00000048656c6c6f2c20576f726c6421" == HexUtils.toHex(clValue.bytes))
```


More example on CLType and CLValue serialization can be found in this test file :

[cltypes/CLValueByteSerializerTest.scala](https://github.com/caspercommunityio/casper-scala-sdk/blob/master/src/test/scala/com/casper/sdk/serialization/cltypes/CLValueByteSerializerTest.scala)

* 