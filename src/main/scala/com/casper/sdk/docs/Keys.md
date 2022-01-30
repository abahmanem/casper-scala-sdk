## Key management

Scala SDK provides following classes for key management:

- CLPublicKey : this class is a holder of the public key. Two algorithms are supported in Casper key management system :
   
    - ED25519
    - SECP256K1

- KeyPair : this class is a holder of the pair represented by a  CLPublicKey and the associated private key.

### Working with public keys 

Scala SDK offers many ways to obtain a  CLPublicKey object

  - Creation from hex String

 ```scala
 val publickey = CLPublicKey("0203e7d5b66b2fd0f66fb0efcceecb673b3762595b30ae1cac48ae8f09d34c952ee4").get
 ```

 - Creation using  byte array and an Algorithm

 ```scala

val bytes : Array[Byte](125,-102,-96,-72,100,19,-41,-1,-102,-111,105,24,44,83,-16,-70,-54,-88,13,52,-62,17,-83,-85,0,126,-44,-121, 106,-15,112, 119)
val publickey = new CLPublicKey(bytes,KeyAlgorithm.ED25519)
 ```

- Lading a  pem file

```scala
val publickey  = CLPublicKey.fromPemFile("/crypto/ED25519_public_key.pem")
```

### Working with key pairs

Scala SDK offers many ways to obtain a KeyPair object 

- Creation using an algorithm

```scala
import com.casper.sdk.crypto.KeyPair

val keyPair  = KeyPair.create(KeyAlgorithm.ED25519)
```

- Loading a private pem file

```scala
import com.casper.sdk.crypto.KeyPair

val keyPair  = KeyPair.loadFromPem("/crypto/ed25519/secret.pem")
```


