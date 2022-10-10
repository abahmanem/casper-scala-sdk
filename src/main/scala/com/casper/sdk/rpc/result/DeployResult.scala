package com.casper.sdk.rpc.result
import com.casper.sdk.domain.deploy.Deploy
import com.casper.sdk.util.CirceConverter


case class DeployResult(api_version:String, deploy:Deploy)


object DeployResult{

  import io.circe.Decoder
  import io.circe.generic.semiauto.deriveDecoder
  implicit val decoder: Decoder[DeployResult] = deriveDecoder[DeployResult]
  }

