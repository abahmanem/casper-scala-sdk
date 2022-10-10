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
                    main_purse: Option[URef],
                    associated_keys : Seq[AssociatedKey],
                    action_thresholds:ActionThresholds
                  )

object Account{

  import io.circe.{Decoder, Encoder}
  import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

  implicit val decoder:Decoder[Account] = deriveDecoder[Account]
  implicit val encoder:Encoder[Account] = deriveEncoder[Account]
}




