package com.casper.sdk.domain
import  com.casper.sdk.types.cltypes.CLType

case class NamedCLTypeArg(
                          val name : String,
                          val cl_type : CLType
                         )
