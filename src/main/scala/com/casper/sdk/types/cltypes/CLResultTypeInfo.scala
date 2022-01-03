package com.casper.sdk.types.cltypes

/**
 * Result CLType CLTypeInfo
 * @param ok
 * @param err
 */
class CLResultTypeInfo(
                        val ok : CLTypeInfo,
                        val err: CLTypeInfo
                      ) extends CLTypeInfo(CLType.Result)

