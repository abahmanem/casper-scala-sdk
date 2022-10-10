package com.casper.sdk.rpc.result

import com.casper.sdk.crypto.hash.Hash

case class DeployHashResult(api_version:String,deploy_hash:Hash)

object DeployHashResult{
  import io.circe.Decoder
  import io.circe.generic.semiauto.deriveDecoder
  implicit val decoder:Decoder[DeployHashResult] = deriveDecoder[DeployHashResult]
}

