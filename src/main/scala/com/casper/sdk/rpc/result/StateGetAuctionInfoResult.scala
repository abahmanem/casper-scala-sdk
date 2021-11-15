package com.casper.sdk.rpc.result
import com.casper.sdk.rpc.RPCError
import com.casper.sdk.rpc.result.RPCResult


/*
case class StateGetAuctionInfoValidatorWeightResult(
  public_key : String,
  weight : String
)

case class StateGetAuctionInfoEraValidatorResult(
  era_id : String,
  validator_weights : List[StateGetAuctionInfoValidatorWeightResult]
)
case class StateGetAuctionInfoDetailBidDelegatorResult(
  public_key : String,
  staked_amount : String,
  bonding_purse : String,
  delegatee : String
)

case class StateGetAuctionInfoDetailBidResult(
  bonding_purse : String,
  staked_amount : String,
  delegation_rate : String,
  delegators : List[StateGetAuctionInfoDetailBidDelegatorResult],
  inactive : String
)

case class StateGetAuctionInfoDetailBidsMapResult(
  public_key : String,
  bid : StateGetAuctionInfoDetailBidResult ,
)

case class StateGetAuctionInfoDetailResult(
  state_root_hash : String,
  block_height : String,
  era_validators : List[StateGetAuctionInfoEraValidatorResult],
  bids : List[StateGetAuctionInfoDetailBidsMapResult]
)

case class StateGetAuctionInfoMapResult
(
  api_version : String,
  auction_state : StateGetAuctionInfoDetailResult
)


 class stateGetAuctionInfoResult
(
  jsonrpc: String,
  id: Long,
  result: Option[StateGetAuctionInfoMapResult],
  error: Option[Error] = None,
) extends RPCResult(jsonrpc, id, result, error)
*/