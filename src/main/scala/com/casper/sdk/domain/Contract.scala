package com.casper.sdk.domain
/*
""" {
  |    "id": 1,
  |    "jsonrpc": "2.0",
  |    "method": "state_get_item",
  |    "params": [
  |        "9ae48819f18df15184e0c353d025d03441189be54f6487e638ced1d48ab1f133",
  |        "hash-d2469afeb99130f0be7c9ce230a84149e6d756e306ef8cf5b8a49d5182e41676"
  |    ]
  |} """.stripMargin
*/

/**
 * Contract entity class
 * @param contract_package_hash
 * @param contract_wasm_hash
 * @param protocol_version
 * @param entry_points
 * @param named_keys
 */
case class Contract (
                 contract_package_hash : String,
                 contract_wasm_hash : String,
                 protocol_version : String,
                 entry_points : Seq[EntryPoint],
                 named_keys : Seq[NamedKey]
               )

object Contract{

  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

  implicit val decoder:Decoder[Contract] = deriveDecoder[Contract]
  implicit val encoder:Encoder[Contract] = deriveEncoder[Contract]
}
