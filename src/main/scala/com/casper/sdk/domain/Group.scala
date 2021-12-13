package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.URef

/**
 * Group
 * @param group
 * @param keys
 */
case class Group(
                  group : String,
                  keys: Seq[URef]
                )
