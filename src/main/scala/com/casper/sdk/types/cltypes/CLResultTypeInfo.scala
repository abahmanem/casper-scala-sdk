package com.casper.sdk.types.cltypes

/**
 * Result CLType CLTypeInfo
 * @param ok
 * @param err
 */
class CLResultTypeInfo(
                        val okCLinfo : CLTypeInfo,
                        val errCLinfo: CLTypeInfo
                      ) extends CLTypeInfo(CLType.Result)

