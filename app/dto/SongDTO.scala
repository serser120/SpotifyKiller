package dto

import sangria.macros.derive._
import sangria.schema._

case class SongDTO(id: Long,
                     name: String,
                     photo: Array[Byte],
                     length: Int,
                     song: Array[Byte])

object SongDTO {
  implicit val ByteArray: ScalarAlias[Array[Byte], String] = ScalarAlias[Array[Byte], String](
    StringType, _.map(_.toChar).mkString, bytea => Right(bytea.getBytes()))
  implicit val songGraphQL = deriveObjectType[Unit, SongDTO](
    ObjectTypeName("SongDTO"),
    ReplaceField("photo", Field("photo", OptionType(ByteArray), resolve = _.value.photo)),
    ReplaceField("song", Field("song", OptionType(ByteArray), resolve = _.value.song)))
}