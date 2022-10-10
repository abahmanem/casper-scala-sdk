package com.casper.sdk.rpc.result

import com.casper.sdk.domain.StoredValue

/**
 *  getDictionaryItem RPC response
 * @param api_version
 * @param dictionary_key
 * @param stored_value
 */
case class DictionaryItemResult(api_version:String, dictionary_key:String,stored_value:StoredValue)
object DictionaryItemResult{
  import io.circe.Decoder
  import io.circe.generic.semiauto.deriveDecoder
  implicit val decoder:Decoder[DictionaryItemResult] = deriveDecoder[DictionaryItemResult]
}
