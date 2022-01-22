package com.casper.sdk.types.cltypes

/**
 * Option  CLTypeInfo
 * @param inner
 */
 class CLOptionTypeInfo extends CLTypeInfo(CLType.Option)
{
  var inner:Option[CLTypeInfo] = None
  def this(innerType:CLTypeInfo) = {
    this()
    inner = Some(innerType)
  }
}
