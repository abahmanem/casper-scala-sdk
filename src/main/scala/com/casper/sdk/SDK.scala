package com.casper.sdk

import com.casper.sdk._
import com.casper.sdk.core._
import com.casper.sdk.result._

import cats.implicits._
import scala.reflect.runtime.universe._

class SDK[F[_]](transport: Transport[F]) (implicit me: MonadException[F]) extends Client[F](transport)  {

  //Milestone #1
  def get_info_get_peers(data: Seq[String]) :  F[GetPeersResult] = call("info_get_peers", data)
  def get_chain_get_state_root_hash(data: Seq[String]) :  F[GetStateRootHashResult] = call("chain_get_state_root_hash", data)
  //Milestone #2
  // Chad
  // def chain_get_block(data: Seq[String]) :  F[GetBlockResult] = call("info_get_deploy", data)
  // def chain_get_block(data: Seq[String]) :  F[GetBlockResult] = call("info_get_status", data)
  // def chain_get_block(data: Seq[String]) :  F[GetBlockResult] = call("chain_get_block_transfers", data)
  // def chain_get_block(data: Seq[String]) :  F[GetBlockResult] = call("chain_get_block", data)
  // def chain_get_block(data: Seq[String]) :  F[GetBlockResult] = call("chain_get_era_info_by_switch_block", data)
  // Mika
  // def chain_get_block(data: Seq[String]) :  F[GetBlockResult] = call("state_get_item", data)
  // def chain_get_block(data: Seq[String]) :  F[GetBlockResult] = call("state_get_dictionary_item", data)
  // def chain_get_block(data: Seq[String]) :  F[GetBlockResult] = call("state_get_balance", data)
  // def chain_get_block(data: Seq[String]) :  F[GetBlockResult] = call("state_get_auction_info", data)


}
object SDK{

}
