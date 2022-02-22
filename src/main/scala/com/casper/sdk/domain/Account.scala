package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.{URef,AccountHash}
import  com.casper.sdk.domain._


/**
 * Account Enttiy class : https://docs.rs/casper-types/1.4.4/casper_types/account/struct.Account.html
 * @param account_hash
 * @param named_keys
 * @param main_purse
 * @param associated_keys
 * @param action_thresholds
 */
case class Account(
                    account_hash :Option[AccountHash],
                    named_keys : Seq[NamedKey],
                    main_purse: URef,
                    associated_keys : Seq[AssociatedKey],
                    action_thresholds:ActionThresholds
                  )



