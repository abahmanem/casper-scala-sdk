package com.casper.sdk.domain

import com.casper.sdk.domain.*
import com.casper.sdk.domain.deploy.*
import com.casper.sdk.types.cltypes.*

class StoredValue(
                   val eraInfo : EraInfo,
                   val deployInfo : DeployInfo,
                   val transfer: Transfer,
                   val contractPackage : ContractPackage,
                   val contract :  Contract,
                   val  contractWASM :  String,
                   val account :  Account,
                   val cLValue : CLValue
                 ) {
}
