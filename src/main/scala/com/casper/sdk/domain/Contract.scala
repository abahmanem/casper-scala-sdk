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
class Contract (
                val contract_package_hash : String,
                val contract_wasm_hash : String,
                val protocol_version : String,
                val entry_points : Seq[EntryPoint],
                val named_keys : Seq[NamedKey]
               )
