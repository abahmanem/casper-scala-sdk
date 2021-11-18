package com.casper.sdk.rpc.result

import com.casper.sdk.domain.Transfer

case class BlockTransfertResult(
                                block_hash: String,
                                transfers: Seq[Transfer]
                               )
