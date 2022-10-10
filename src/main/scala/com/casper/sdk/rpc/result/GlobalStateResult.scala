package com.casper.sdk.rpc.result

import com.casper.sdk.domain.{BlockHeader, StoredValue}

/**
 * queryGlobalstate RPC response
 * @param api_version
 * @param block_header
 * @param stored_value
 */
case class GlobalStateResult(api_version:String, block_header: Option[BlockHeader], stored_value:StoredValue)

object GlobalStateResult{
  import io.circe.syntax._
  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
  implicit val decoder:Decoder[GlobalStateResult] = deriveDecoder[GlobalStateResult]
 }
