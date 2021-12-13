package com.casper.sdk.domain

/**
 * ActionThresholds entity class : https://docs.rs/casper-types/1.4.4/casper_types/account/action_thresholds/struct.ActionThresholds.html
 * @param deployment
 * @param key_management
 */
case class ActionThresholds(
                             deployment: Int,
                             key_management:Int
                           )
