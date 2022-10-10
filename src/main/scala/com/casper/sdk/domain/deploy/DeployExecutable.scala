package com.casper.sdk.domain.deploy

import com.casper.sdk.crypto.hash.Hash
import com.casper.sdk.serialization.BytesSerializable
import com.casper.sdk.types.cltypes.{AccountHash, CLType, CLTypeInfo, CLValue}
import com.casper.sdk.util.HexUtils
import io.circe.{Decoder, Encoder, HCursor, Json}
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

/**
 * DeployExecutable
 * @param args
 */

sealed abstract class DeployExecutable (val args: Seq[DeployNamedArg]) {
  def tag: Int
}

object DeployExecutable{
  implicit val decoder: Decoder[DeployExecutable] = deriveDecoder[DeployExecutable]
  implicit val encoder: Encoder[DeployExecutable] = deriveEncoder[DeployExecutable]
}

/**
 * StoredContractByHash entity object
 *
 * @param hash
 * @param entry_point
 * @param args
 */
case class StoredContractByHash(
                                 hash:Hash,
                                 entry_point:String,
                                 override val args: Seq[DeployNamedArg]
                               )  extends DeployExecutable(args) {
  override def tag=1
}

object StoredContractByHash {
  implicit val encoder: Encoder[StoredContractByHash] = new Encoder[StoredContractByHash] {
    final def apply(value: StoredContractByHash): Json = Json.obj(
          ("hash", Json.fromString(HexUtils.toHex(value.hash.hash).getOrElse(""))),
          ("entry_point", Json.fromString(value.entry_point)),
          ("args", value.args.asJson)
    )
  }

  implicit val decoder: Decoder[StoredContractByHash] = new Decoder[StoredContractByHash] {
    final def apply(c: HCursor): Decoder.Result[StoredContractByHash] =
      for {
        hash <- c.downField("hash").as[String]
        entry_point <- c.downField("entry_point").as[String]
          args <- c.downField("args").as[Seq[DeployNamedArg]]
      } yield {
           new StoredContractByHash(Hash(HexUtils.fromHex(hash).getOrElse(Array.emptyByteArray)), entry_point,args)
      }
  }
}


/**
 *
 * @param modules_bytes
 * @param args
 */
case class ModuleBytes(
                        module_bytes: Array[Byte],
                        override val args: Seq[DeployNamedArg]
                      ) extends DeployExecutable(args) {
  override def tag = 0
}

object ModuleBytes {
  implicit val encode: Encoder[ModuleBytes] = new Encoder[ModuleBytes] {
    final def apply(value: ModuleBytes): Json = Json.obj(
          ("module_bytes",Json.fromString(HexUtils.toHex(value.module_bytes).getOrElse(""))),
          ("args", value.args.asJson )
        )
  }

  implicit val decoder: Decoder[ModuleBytes] = new Decoder[ModuleBytes] {
    final def apply(c: HCursor): Decoder.Result[ModuleBytes] =
      for {
        foo <- c.downField("module_bytes").as[String]
        args <- c.downField("args").as[Seq[DeployNamedArg]]
     } yield {
        new ModuleBytes(foo.getBytes, args)
      }
  }

  /**
   * create a ModuleBytes with a payment amount
   *
   * @param amount
   * @return
   */
  def apply(amount: BigInt): ModuleBytes = {
    var args = Seq[DeployNamedArg]()
    args = args.+:(new DeployNamedArg("amount", CLValue.U512(amount)))
    new ModuleBytes(Array.empty[Byte], args)

  }
}


/**
 * DeployTransfer Entity
 *
 * @param args
 */
case class Transfer(
                           override val args: Seq[DeployNamedArg]
                         ) extends DeployExecutable(args) {
  override def tag = 5
}

/**
 * companion object
 */

object Transfer {
  implicit val encoder: Encoder[Transfer] = new Encoder[Transfer] {
    final def apply(value: Transfer): Json = Json.obj(
          ("args", value.args.asJson)
     )
  }

  implicit val decoder: Decoder[Transfer] = new Decoder[Transfer] {
    final def apply(c: HCursor): Decoder.Result[Transfer] =
      for {
        args <- c.downField("args").as[Seq[DeployNamedArg]]
      } yield {
        Transfer(args)
      }
  }
  /**
   * Transfert to an accountHash
   *
   * @param amount to transfer
   * @param to     recipient account
   * @param id
   * @return DeployTransfer
   */
  def apply(amount: Long, to: AccountHash, id: BigInt): Transfer = {
    var args = Seq[DeployNamedArg]()
    args = args.+:(new DeployNamedArg("amount", CLValue.U512(amount)))
       .+:(new DeployNamedArg("target", CLValue.ByteArray(to.bytes)))
      .+:(new DeployNamedArg("id", if (id == null) CLValue.OptionNone(new CLTypeInfo(CLType.U64)) else CLValue.Option(CLValue.U64(id))))
    new Transfer(args)
  }
}


  /**
 * StoredContractByName entity object
 *
 * @param name
 * @param entry_point
 * @param args
 */
case class StoredContractByName(
                                 name: String,
                                 entry_point: String,
                                 override val args: Seq[DeployNamedArg]
                               ) extends DeployExecutable(args) {
  override def tag = 2
}

object StoredContractByName {
  implicit val encoder: Encoder[StoredContractByName] = new Encoder[StoredContractByName] {
    final def apply(value: StoredContractByName): Json = Json.obj(
          ("name", Json.fromString(value.name)),
          ("entry_point", Json.fromString(value.entry_point)),
          ("args", value.args.asJson)
    )
  }

  implicit val decoder: Decoder[StoredContractByName] = new Decoder[StoredContractByName] {
    final def apply(c: HCursor): Decoder.Result[StoredContractByName] =
      for {
        name <- c.downField("name").as[String]
        entry_point <- c.downField("entry_point").as[String]
        args <- c.downField("args").as[Seq[DeployNamedArg]]
      } yield {
        new StoredContractByName(name,entry_point, args)
      }
  }
}



/**
 * StoredVersionedContractByHash entity object
 *
 * @param hash
 * @param version
 * @param entry_point
 * @param args
 */
case class StoredVersionedContractByHash(
                                          hash: Hash,
                                          version: Int,
                                          entry_point: String,
                                          override val args: Seq[DeployNamedArg]
                                        ) extends DeployExecutable(args) {

  override def tag = 3
}

object StoredVersionedContractByHash {
  implicit val encoder: Encoder[StoredVersionedContractByHash] = new Encoder[StoredVersionedContractByHash] {
    final def apply(value: StoredVersionedContractByHash): Json =
        Json.obj(
          ("hash", value.hash.asJson),
          ("version", Json.fromInt(value.version)),
          ("entry_point", Json.fromString(value.entry_point)),
          ("args", value.args.asJson)
       )
  }
  implicit val decoder: Decoder[StoredVersionedContractByHash] = new Decoder[StoredVersionedContractByHash] {
    final def apply(c: HCursor): Decoder.Result[StoredVersionedContractByHash] =
      for {
        hash <- c.downField("hash").as[Hash]
        version <- c.downField("version").as[Int]
        entry_point <- c.downField("entry_point").as[String]
        args <- c.downField("args").as[Seq[DeployNamedArg]]
      } yield {
        new StoredVersionedContractByHash(hash,version,entry_point, args)
      }
  }

}

/**
 * StoredVersionedContractByName entity object
 *
 * @param name
 * @param version
 * @param entry_point
 * @param args
 */
case class StoredVersionedContractByName(
                                          name: String,
                                          version: Int,
                                          entry_point: String,
                                          override val args: Seq[DeployNamedArg]
                                        ) extends DeployExecutable(args) {
  override def tag = 4
}
object StoredVersionedContractByName {
  implicit val encode: Encoder[StoredVersionedContractByName] = new Encoder[StoredVersionedContractByName] {
    final def apply(value: StoredVersionedContractByName): Json = Json.obj(
          ("name", Json.fromString(value.name)),
          ("version", Json.fromInt(value.version)),
          ("entry_point", Json.fromString(value.entry_point)),
          ("args", value.args.asJson)
      )
  }

  implicit val decoder: Decoder[StoredVersionedContractByName] = new Decoder[StoredVersionedContractByName] {
    final def apply(c: HCursor): Decoder.Result[StoredVersionedContractByName] =
      for {
        name <- c.downField("name").as[String]
        version <- c.downField("version").as[Int]
        entry_point <- c.downField("entry_point").as[String]
        args <- c.downField("args").as[Seq[DeployNamedArg]]
      } yield {
        new StoredVersionedContractByName(name, version, entry_point, args)
      }
  }
}

