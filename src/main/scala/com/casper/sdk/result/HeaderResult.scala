package com.casper.sdk.result

import  com.casper.sdk.core.Error

case class HeaderResult
(
  parent_hash : String,
  state_root_hash : String,
  body_hash : String,
  random_bit : String,
  accumulated_seed: String,
  era_end : String,
  timestamp : String,
  era_id : String,
  height : String,
  protocol_version : String
)
