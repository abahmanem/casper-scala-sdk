package com.casper.sdk.rpc.result
import com.casper.sdk.domain.deploy.Deploy
import com.casper.sdk.util.CirceConverter

/**
 * getDeploy RPC response
 * @param api_version
 * @param deploy
 */
case class DeployResult(api_version:String, deploy:Deploy)


object DeployResult{

  import io.circe.Decoder
  import io.circe.generic.semiauto.deriveDecoder
  implicit val decoder: Decoder[DeployResult] = deriveDecoder[DeployResult]
  }

