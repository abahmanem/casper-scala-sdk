package com.casper.sdk.domain.deploy

import com.casper.sdk.types.cltypes.{CLPublicKey, Signature}
case class DeployApproval(
                      signer : CLPublicKey ,
                      signature : Signature
                    )