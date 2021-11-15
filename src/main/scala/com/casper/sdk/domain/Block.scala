package com.casper.sdk.domain


case class Block(hash: String,
                 header: BlockHeader,
                 body: BlockBody,
                 proofs: List[BlockProof]
                )
