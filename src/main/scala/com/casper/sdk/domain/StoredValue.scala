package com.casper.sdk.domain

import com.casper.sdk.domain.*
import com.casper.sdk.domain.deploy.*
import com.casper.sdk.types.cltypes.*

/**
 * StoredValue entity class
 * @param eraInfo
 * @param deployInfo
 * @param transfer
 * @param contractPackage
 * @param Contract
 * @param contractWASM
 * @param Account
 * @param CLValue
 */
case class StoredValue(
                   val eraInfo : EraInfo,
                   val deployInfo : DeployInfo,
                   val transfer: Transfer,
                   val contractPackage : ContractPackage,
                   val Contract :  Contract,
                   val contractWASM :  String,
                   val Account :  Account,
                   val CLValue : CLValue
                 )