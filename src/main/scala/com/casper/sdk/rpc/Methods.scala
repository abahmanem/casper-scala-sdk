package com.casper.sdk.rpc

/**
 * Enum with available RPC methods
 */
enum Method (val name: String) {
  case STATE_GET_ITEM extends  Method ("")
  case INFO_GET_PEERS extends  Method ("info_get_peers")
  case STATE_ROOT_HASH extends  Method ("chain_get_state_root_hash")
  case INFO_GET_STATUS extends  Method ("")
}
