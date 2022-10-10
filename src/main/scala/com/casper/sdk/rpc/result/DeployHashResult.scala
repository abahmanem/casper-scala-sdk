package com.casper.sdk.rpc.result

import com.casper.sdk.crypto.hash.Hash

/**
 * putDeploy RPC response
 * @param api_version
 * @param deploy_hash
 */
case class DeployHashResult(api_version:String,deploy_hash:Hash)

object DeployHashResult{
  import io.circe.Decoder
  import io.circe.generic.semiauto.deriveDecoder
  implicit val decoder:Decoder[DeployHashResult] = deriveDecoder[DeployHashResult]
}

