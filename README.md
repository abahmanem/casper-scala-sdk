
<p align="center"><a href="https://analytics.caspercommunity.io" target="_blank"><img src="https://analytics.caspercommunity.io/assets/icon/android-chrome-512x512.png" width="150"></a></p>

## About Casper Scala SDK

Scala client library for interracting with CasperLabs nodes.

Current version :1.2.1

# How to install

## SBT

In your build.sbt, add :


```scala
 libraryDependencies += "io.caspercommunity" %% "casper-scala-sdk" % "1.2.1"
```

## Maven

In your maven pom file add :

```scala
<dependency>
  <groupId>io.caspercommunity</groupId>
  <artifactId>casper-scala-sdk_${scala.version}</artifactId>
  <version>1.2.1</version>
</dependency>  

```
scala.version is the scala version used, ex = 3

## Documentation:

### API

* [casper-scala-sdk](https://caspercommunityio.github.io/casper-scala-sdk/api/api/)

### Serialization

* [Serialization standards](https://github.com/caspercommunityio/casper-scala-sdk/blob/master/src/main/scala/com/casper/sdk/docs/Serialization.md#serialization)
* [Examples](https://github.com/caspercommunityio/casper-scala-sdk/blob/master/src/main/scala/com/casper/sdk/docs/Serialization.md#example)

### Key Management

* [CLPublicKey](https://github.com/caspercommunityio/casper-scala-sdk/blob/master/src/main/scala/com/casper/sdk/docs/Keys.md#working-with-public-keys)
* [KeyPair](https://github.com/caspercommunityio/casper-scala-sdk/blob/master/src/main/scala/com/casper/sdk/docs/Keys.md#Working-with-key-pairs)


### Casper-Scala-Sdk RPC

* [getPeers](https://github.com/caspercommunityio/casper-scala-sdk/blob/master/src/main/scala/com/casper/sdk/docs/RPC.md#get-peers)
* [getStateRootHash](https://github.com/caspercommunityio/casper-scala-sdk/blob/master/src/main/scala/com/casper/sdk/docs/RPC.md#get-state_root_hash)
* [getBlock by hash](https://github.com/caspercommunityio/casper-scala-sdk/blob/master/src/main/scala/com/casper/sdk/docs/RPC.md#get-block-by-hash)
* [getBlock by height](https://github.com/caspercommunityio/casper-scala-sdk/blob/master/src/main/scala/com/casper/sdk/docs/RPC.md#get-block-by-height)
* [getStatus](https://github.com/caspercommunityio/casper-scala-sdk/blob/master/src/main/scala/com/casper/sdk/docs/RPC.md#get-status)
* [getBlockTransfers](https://github.com/caspercommunityio/casper-scala-sdk/blob/master/src/main/scala/com/casper/sdk/docs/RPC.md#get-block-transfers)
* [getAuctionInfo](https://github.com/caspercommunityio/casper-scala-sdk/blob/master/src/main/scala/com/casper/sdk/docs/RPC.md#get-auction-state)
* [getDeploy](https://github.com/caspercommunityio/casper-scala-sdk/blob/master/src/main/scala/com/casper/sdk/docs/RPC.md#get-deploy)
* [getEraInfoBySwitchBlock](https://github.com/caspercommunityio/casper-scala-sdk/blob/master/src/main/scala/com/casper/sdk/docs/RPC.md#get-era-summary-by-switch-block-hash)
* [getStateItem](https://github.com/caspercommunityio/casper-scala-sdk/blob/master/src/main/scala/com/casper/sdk/docs/RPC.md#get--state-item)
* [getBalance](https://github.com/caspercommunityio/casper-scala-sdk/blob/master/src/main/scala/com/casper/sdk/docs/RPC.md#get-account-balance)
* [getDictionaryItem](https://github.com/caspercommunityio/casper-scala-sdk/blob/master/src/main/scala/com/casper/sdk/docs/RPC.md#get-dictionary-item)
* [putDeploy](https://github.com/caspercommunityio/casper-scala-sdk/blob/master/src/main/scala/com/casper/sdk/docs/RPC.md#put-deploy)



## Running unit tests

### Requirements:

#### JAVA
Make sure you have the Java 8 JDK (also known as 1.8)

If you don’t have version 1.8 or higher, install the JDK

#### SBT

Install sbt (version 1.5.2 or higher):

Mac :  https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Mac.html

Windows :  https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Windows.html

Linux : https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Linux.html


### Clone the project

```scala
git clone https://github.com/caspercommunityio/casper-scala-sdk
```

### Run the unit tests :

```scala
cd casper-scala-sdk

sbt test

```
### Generate project artefact

```scala
sbt package
```

This will generate : casper-scala-sdk_${scala.version}{version}.jar.


# Usage examples

## Create a Client

Pass the url of the node  to constructor

```scala

val client = new CasperSdk("http://node_ip_address:7777/rpc")

```

## RPC Calls

### Get network peers list  

Retrieves  the list of connected peers.

```scala
val tryPeers:Try[Seq[Peer]] = client.getPeers()
val listOfPeers = tryPeers.getOrElse(List.empty[Peer])

```

### Get State Root Hash  

Retrieves  the state root hash String.

```scala
 val tryStateRootHash:Try[String] = client.getStateRootHash("")
```

### Get Block

Retrieves a Block object.

   #### using block hash :

call parameters :
- block hash

```scala
val tryBlock:Try[Block] = client.getBlock("74dce8911A3EDf0f872dC11F0a63Ca9fE1b55b7188a9Feaaf431518bF9c508B4")
```

   #### using block height :

call parameters :
- block height


```scala
val tryBlock:Try[Block] = client.getBlockByHeight(371608)
```

### Get Deploy

Retrieves a Deploy object.

call parameters :
- deploy hash

```scala
val tryDeploy:Try[Deploy] = getDeploy("5545207665f6837F44a6BCC274319280B73a6f0997F957A993e60f878A736678")
```

###  Get Node Status

Retrieves a NodeStatus object.

````scala
 val tryNodeSatatus:Try[NodeSatatus] = client.getStatus
````

### Get BlockTransfers

Retrieves Transfert List within a block.

call parameters :
- block hash

```scala
val tryTransfers = client.getBlockTransfers("a623841478381D78C769636582305ef724f561d7314B4daED19A3EA6373Dd778")
```

### Get current auction state

Retrieves an AutionState object.

call parameters :
- block hash

```scala
val tryAuctionInfo = client.getAuctionInfo("3a4EfA0AA223bF713bEDB5fa8D6dEc29a008C923aec0ACB02A3e4e449b9E01a8")
```

can also be called without parameters :

```scala
val auctionInfo = client.getAuctionInfo("")
```

### Get EraInfo By Switch Block

Retrieves an EraSummury object.

call parameters :
- switch block (last block within an era) hash

```scala
val tryErasummury = client.getEraInfoBySwitchBlock("1e46B4c173dB70fDE0E867FF679ACa24e1c5Bea3C4333af94e53B4E3BC548B6B")
```

### Get StateItem

Retrieves a StoredValue object.

It's one of three possible values :

   #### A contract :

call parameters :

- state root hash
- contract hash

````scala
val tryStoredValue = client.getStateItem("30cE5146268305AeeFdCC05a5f7bE7aa6dAF187937Eed9BB55Af90e1D49B7956","hash-4dd10a0b2a7672e8ec964144634ddabb91504fe50b8461bac23584423318887d",Seq.empty)
var contract : Contrat = null
if (tryStoredValue.isSuccess)
   contract =tryStoredValue.get.Contract
````

  #### An account  :

call parameters :

- state root hash
- account hash

````scala
val tryStoredValue = client.getStateItem("30cE5146268305AeeFdCC05a5f7bE7aa6dAF187937Eed9BB55Af90e1D49B7956","account-hash-46dE97966cfc2F00C326e654baD000AB7a5E26bEBc316EF4D74715335cF32A88",Seq.empty)
var account : Account = null
if (tryStoredValue.isSuccess)
   account =tryStoredValue.get.Account

````

#### A CLValue  :

call parameters :

- state root hash
- account hash

````scala
val storedValue = client.getStateItem("30cE5146268305AeeFdCC05a5f7bE7aa6dAF187937Eed9BB55Af90e1D49B7956","account-hash-46dE97966cfc2F00C326e654baD000AB7a5E26bEBc316EF4D74715335cF32A88",Seq.empty)
var clValue : CLValue = null
if (tryStoredValue.isSuccess)
   clValue =tryStoredValue.get.CLValue
````

### Get DictionaryItem

Retrieves a CLValue object.

call parameters :

- state root hash
- item key
- seed uref hash

```scala
val tryStoredVvalue = client.getDictionaryItem("8180307A39A8583a4a164154C360FB9Ab9B15A5B626295635A62DFc7A82e66a3",
      "a8261377ef9cf8e741dd6858801c71e38c9322e66355586549b75ab24bdd73f2","uref-F5ea525E6493B41DC3c9b196ab372b6F3f00cA6F1EEf8fe0544e7d044E5480Ba-007")
var clValue : CLValue = null
if (tryStoredValue.isSuccess)
   clValue =tryStoredValue.get.CLValue
```

### Get Balance

Retrieves the balances(in motes) of an account

call parameters :

- state root hash
- account uref hash

```scala
 val  tryBalance = client.getBalance("30cE5146268305AeeFdCC05a5f7bE7aa6dAF187937Eed9BB55Af90e1D49B7956",new URef("uref-9cC6877ft07c211e44068D5dCc2cC28A67Cb582C3e239E83Bb0c3d067C4D0363-007"))

```

## License

The Casper Scala SDK is open-sourced software licensed under the [MIT license](https:sbt//opensource.org/licenses/MIT).
