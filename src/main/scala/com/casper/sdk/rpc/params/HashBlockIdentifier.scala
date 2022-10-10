package com.casper.sdk.rpc.params

/**
 * Hash Block Identifier
 * @param hash
 */
case class HashBlockIdentifier(var hash:String)  extends BlockIdentifier with StateIdentifier
