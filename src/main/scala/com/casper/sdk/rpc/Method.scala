package com.casper.sdk.rpc

/**
 * Enum with Capser netowrk RPC available  methods
 */
enum Method(val name: String) {
  case QUERY_GLOBAL_STATE extends Method("query_global_state")
  case STATE_GET_ACCOUNT_INFO extends Method("state_get_account_info")
  case CHAIN_GET_BLOCK extends Method("chain_get_block")
  case INFO_GET_PEERS extends Method("info_get_peers")
  case STATE_ROOT_HASH extends Method("chain_get_state_root_hash")
  case INFO_GET_STATUS extends Method("info_get_status")
  case CHAIN_GET_BLOCK_TRANSFERS extends Method("chain_get_block_transfers")
  case INFO_GET_DEPLOY extends Method("info_get_deploy")
  case STATE_GET_AUCTION_INFO extends Method("state_get_auction_info")
  case CHAIN_GET_ERA_INFO_BY_SWITCH_BLOCK extends Method("chain_get_era_info_by_switch_block")
  //case STATE_GET_ITEM extends Method("state_get_item")
  case STATE_GET_BALANCE extends Method("state_get_balance")
  case STATE_GET_DICTIONARY_ITEM extends Method("state_get_dictionary_item")
  case ACCOUNT_PUT_DEPLOY extends Method("account_put_deploy")
  case RPC_SCHEMA extends Method("rpc.discover")
 }


