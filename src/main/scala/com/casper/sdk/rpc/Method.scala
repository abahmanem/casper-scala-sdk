package com.casper.sdk.rpc

/**
 * Enum with Capser netowrk RPC available  methods
 */

enum Method(val name: String) {
  //TODO add state_get_account_info

  //TODO query_global_state
  /**
   *  "name": "query_global_state",
                    "summary": "a query to global state using either a Block hash or state root hash",
                    "params": [
                        {
                            "name": "state_identifier",
                            "schema": {
                                "description": "The identifier used for the query.",
                                "$ref": "#/components/schemas/GlobalStateIdentifier"
                            },
                            "required": true
                        },
                        {
                            "name": "key",
                            "schema": {
                                "description": "`casper_types::Key` as formatted string.",
                                "type": "string"
                            },
                            "required": true
                        },
                        {
                            "name": "path",
                            "schema": {
                                "description": "The path components starting from the key as base.",
                                "default": [],
                                "type": "array",
                                "items": {
                                    "type": "string"
                                }
                            },
                            "required": false
                        }
   */






  case CHAIN_GET_BLOCK extends Method("chain_get_block")


  /**
   * {
                    "name": "info_get_peers",
                    "summary": "returns a list of peers connected to the node",
                    "params": [],
                    "result": {
                        "name": "info_get_peers_result",
                        "schema": {
                            "description": "Result for \"info_get_peers\" RPC response.",
                            "type": "object",
                            "required": [
                                "api_version",
                                "peers"
                            ],
                            "properties": {
                                "api_version": {
                                    "description": "The RPC API version.",
                                    "type": "string"
                                },
                                "peers": {
                                    "description": "The node ID and network address of each connected peer.",
                                    "$ref": "#/components/schemas/PeersMap"
                                }
                            },
                            "additionalProperties": false
                        }
                    },
   */
  case INFO_GET_PEERS extends Method("info_get_peers")




  case STATE_ROOT_HASH extends Method("chain_get_state_root_hash")
  case INFO_GET_STATUS extends Method("info_get_status")
  case CHAIN_GET_BLOCK_TRANSFERTS extends Method("chain_get_block_transfers")
  case INFO_GET_DEPLOY extends Method("info_get_deploy")
  case STATE_GET_AUCTION_INFO extends Method("state_get_auction_info")
  case CHAIN_GET_ERA_INFO_BY_SWITCH_BLOCK extends Method("chain_get_era_info_by_switch_block")
  case STATE_GET_ITEM extends Method("state_get_item")



  case STATE_GET_BALANCE extends Method("state_get_balance")

  /**
   * {
                    "name": "state_get_dictionary_item",
                    "summary": "returns an item from a Dictionary",
                    "params": [
                        {
                            "name": "state_root_hash",
                            "schema": {
                                "description": "Hash of the state root",
                                "$ref": "#/components/schemas/Digest"
                            },
                            "required": true
                        },
                        {
                            "name": "dictionary_identifier",
                            "schema": {
                                "description": "The Dictionary query identifier.",
                                "$ref": "#/components/schemas/DictionaryIdentifier"
                            },
                            "required": true
                        }
                    ],
   */
  case STATE_GET_DICTIONARY_ITEM extends Method("state_get_dictionary_item")
  case ACCOUNT_PUT_DEPLOY extends Method("account_put_deploy")
  case RPC_SCHEMA extends Method("rpc.discover")
 }
