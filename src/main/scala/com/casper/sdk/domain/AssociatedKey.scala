package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.AccountHash

/**
 * AssociatedKeys entity class : https://docs.rs/casper-types/1.4.4/casper_types/account/associated_keys/struct.AssociatedKeys.html
 *
 * @param account_hash
 * @param weight
 */
case class AssociatedKey(
                          account_hash: Option[AccountHash],
                          weight:Int
                        )
