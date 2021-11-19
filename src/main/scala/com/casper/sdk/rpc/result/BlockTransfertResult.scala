package com.casper.sdk.rpc.result

import com.casper.sdk.domain.Transfer

/**
 * Case class for BlockTransfertResult
 * @param block_hash
 * @param transfers
 */
case class BlockTransfertResult(
                                block_hash: String,
                                transfers: Seq[Transfer]
                               )
