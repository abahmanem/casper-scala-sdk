package com.casper.sdk.types.cltypes

class CLResultTypeInfo(val ok : CLTypeInfo, val err: CLTypeInfo) extends CLTypeInfo(CLType.Result)

