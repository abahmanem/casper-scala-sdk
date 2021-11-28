package com.casper.sdk.domain.deploy

trait BytesSerializable {
  def encode() : Array[Byte]
}
