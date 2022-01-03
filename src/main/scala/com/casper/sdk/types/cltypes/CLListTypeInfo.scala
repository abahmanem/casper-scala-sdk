package com.casper.sdk.types.cltypes

/**
 *  List CLType  CLTypeInfo
 * @param size
 * @param cltypeInfo
 */
class CLListTypeInfo(
                      val size: Int,
                      val cltypeInfo: CLTypeInfo
                    ) extends CLTypeInfo(CLType.List)
