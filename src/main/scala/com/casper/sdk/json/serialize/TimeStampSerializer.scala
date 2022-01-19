package com.casper.sdk.json.serialize


import com.casper.sdk.util.TimeUtil
import com.fasterxml.jackson.databind.util.StdConverter

/**
 * Custom Json Serializer for TimeStamp : 1620138035104---->"2021-05-04T14:20:35.104Z"
 */
class TimeStampSerializer extends  StdConverter[Long,String] {

  override def convert(value:Long) : String={
    TimeUtil.timeStampString(value)
  }
}
