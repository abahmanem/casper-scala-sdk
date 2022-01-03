package com.casper.sdk.types.cltypes

/**
 * CLTuple2 CLType CLTypeInfo
 * @param typeinfo1
 * @param typeinfo2
 */
class CLTuple2TypeInfo(
                        val typeinfo1 : CLTypeInfo,
                        val typeinfo2 : CLTypeInfo
                      ) extends CLTypeInfo(CLType.Tuple2)
