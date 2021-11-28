package com.casper.sdk.types.cltypes

import com.casper.sdk.json.deserialize.PublicKeyDeserializer
import com.casper.sdk.types.cltypes
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
@JsonDeserialize(`using` = classOf[PublicKeyDeserializer])
class CLPublicKey(
                   override val bytes: Array[Byte]
                 )  extends CLValue(bytes,CLType.PublicKey){

 var keyAlgorithm : KeyAlgorithm=null
 def this(key : String) = this(key.getBytes().array)
 def this(key : String, algo : KeyAlgorithm) = {
    this(key.getBytes())
    keyAlgorithm = algo
  }
}
