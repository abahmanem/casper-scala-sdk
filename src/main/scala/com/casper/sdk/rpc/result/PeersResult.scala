package com.casper.sdk.rpc.result

import com.casper.sdk.domain.Peer

/**
 * Case class for PeersResult
 * @param peers
 */
case class PeersResult(peers: List[Peer])