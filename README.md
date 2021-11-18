
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


# Example usage

## Create a Client

Pass the url of the node  to constructor 

```
import com.casper.sdk.util.implicits.*

val client = new CasperSdk("http://localhost:7777/rpc")


```


## RPC Calls

### Get network peers list  

```
val peersList = client.info_get_peers()

```

### Get State Root Hash  

```
 val state_root_hash = client.state_root_hash("")

```
...

## How to test

Run the unit tests :

```

sbt test

```

## TODOS :


## License

The Casper Scala SDK is open-sourced software licensed under the [MIT license](https:sbt//opensource.org/licenses/MIT).
