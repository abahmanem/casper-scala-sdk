package com.casper.sdk.types.cltypes

import com.casper.sdk.json.serialize.CLTypeInfoSerializer
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 * CLType info holder
 *
 * @param cl_Type
 */
@JsonSerialize(`using` = classOf[CLTypeInfoSerializer])
case class CLTypeInfo (
                        cl_Type: CLType
                      )
