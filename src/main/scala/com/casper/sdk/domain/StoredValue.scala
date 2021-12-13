package com.casper.sdk.domain

import com.casper.sdk.domain.*
import com.casper.sdk.domain.deploy.*
import com.casper.sdk.types.cltypes.*

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