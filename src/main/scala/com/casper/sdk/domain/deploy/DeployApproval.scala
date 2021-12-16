package com.casper.sdk.domain.deploy

import  com.casper.sdk.types.cltypes.CLPublicKey
case class DeployApproval(
                      signer : CLPublicKey ,
                      signature : String
                    )