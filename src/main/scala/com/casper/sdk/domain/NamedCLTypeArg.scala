package com.casper.sdk.domain
import  com.casper.sdk.types.cltypes.CLType
import  com.casper.sdk.types.cltypes.CLValue

/**
 * NamedCLTypeArg entity class
 * @param name
 * @param cl_type
 */
case class NamedCLTypeArg(
                          val name : String,
                          val cl_type : CLType
                         )
