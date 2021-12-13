package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.CLType
import com.fasterxml.jackson.module.scala.JsonScalaEnumeration

/**
 * EntryPoint entity class
 * @param name
 * @param entry_point_type
 * @param access
 * @param ret
 * @param args
 */
case class EntryPoint(
                     val name:String,
                     val entry_point_type : String,
                     val access :String,
                     val ret: CLType,
                     val args: Seq[NamedCLTypeArg]
                     )
