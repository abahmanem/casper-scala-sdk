package com.casper.sdk.domain

import com.casper.sdk.types.cltypes.CLType


case class EntryPoint(
                     val name:String,
                     val entry_point_type : String,
                     val access :String,
                     val ret: CLType,
                     val args: Seq[NamedCLTypeArg]
                     )
