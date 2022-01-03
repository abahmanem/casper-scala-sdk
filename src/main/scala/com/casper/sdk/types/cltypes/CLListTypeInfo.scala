package com.casper.sdk.types.cltypes

class CLListTypeInfo(
                      val size: Int,
                      val cltypeInfo: CLTypeInfo
                    ) extends CLTypeInfo(CLType.List)
