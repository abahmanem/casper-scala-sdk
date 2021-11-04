package com.casper.sdk

import com.casper.sdk._
import com.casper.sdk.core._
import com.casper.sdk.result._

import cats.implicits._
import scala.reflect.runtime.universe._

class SDK[F[_]](transport: Transport[F]) (implicit me: MonadException[F]) extends Client[F](transport)  {

  // def get_help(data: Seq["Lits all available calls (like unix man command) "]) :  F[String] = call("help", data)
  // def get_info_get_deploy(data: Seq[String]) :  F[String] = call("info_get_deploy", data)
  def get_info_get_peers(data: Seq[String]) :  F[GetPeersResult] = call("info_get_peers", data)
  def get_chain_get_state_root_hash(data: Seq[String]) :  F[GetStateRootHashResult] = call("chain_get_state_root_hash", data)
  def chain_get_block(data: Seq[String]) :  F[GetBlockResult] = call("chain_get_block", data)
  // def get_state_get_auction_info(data: Seq[String]) :  F[String] = call("state_get_auction_info", data)


}
object SDK{

}
