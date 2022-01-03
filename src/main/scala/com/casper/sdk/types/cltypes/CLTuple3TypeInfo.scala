package com.casper.sdk.types.cltypes

/**
 * CLTuple3 CLType CLTypeInfo
 * @param typeinfo1
 * @param typeinfo2
 * @param typeinfo3
 */
class CLTuple3TypeInfo(
                        val typeinfo1 : CLTypeInfo,
                        val typeinfo2 : CLTypeInfo,
                        val typeinfo3 : CLTypeInfo
                      )  extends CLTypeInfo(CLType.Tuple3)
