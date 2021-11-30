
<p align="center"><a href="https://analytics.caspercommunity.io" target="_blank"><img src="https://analytics.caspercommunity.io/assets/icon/android-chrome-512x512.png" width="150"></a></p>

## About Casper Scala SDK

Scala client library for interracting with CasperLabs nodes. 


# How to install

## SBT

In your build.sbt, add :


```
 libraryDependencies +=  "network.casper" %% "casper-scala-sdk" % "M.m.i"
 
```


## Maven

In your maven pom file add :

```
<dependency>
  <groupId>network.casper</groupId>
  <artifactId>casper-scala-sdk_${scala.version}</artifactId>
  <version>M.m.i</version>
</dependency>  

```

M.m.i is the version number , example 1.0.0


# Usage examples 

## Create a Client

Pass the url of the node  to constructor 

```
import com.casper.sdk.util.implicits.*

val client = new CasperSdk("http://localhost:7777/rpc")


```


## RPC Calls

### Get network peers list  

```
val peersList = client.getPeers()

```

### Get State Root Hash  

```
 val stateRootHash = client.getStateRootHash("")

```
...

## How to test

### Requirements:

#### Java
Make sure you have the Java 8 JDK (also known as 1.8)

If you don’t have version 1.8 or higher, install the JDK

#### sbt

Install sbt (version 1.5.2 or higher): 

Mac :  https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Mac.html

Windows :  https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Windows.html

Linux : https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Linux.html


### clone the project 

```
git clone https://github.com/caspercommunityio/casper-scala-sdk
```

### Run the unit tests :

```
cd casper-scala-sdk

sbt test

```

## TODOS :
-

## License

The Casper Scala SDK is open-sourced software licensed under the [MIT license](https:sbt//opensource.org/licenses/MIT).
