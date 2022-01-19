package com.casper.sdk.types.cltypes

/**
 * Option  CLTypeInfo
 * @param inner
 */
 class CLOptionTypeInfo extends CLTypeInfo(CLType.Option)
{
  var inner:CLTypeInfo = null
  def this(innerType:CLTypeInfo) = {
    this()
    inner = innerType
  }
}
