package dto

import sangria.macros.derive._
import sangria.schema._

case class SingerDTO(id: Long,
                     name: String,
                     photo: Array[Byte],
                     numOfPlays: Long,
                     songs: Seq[SongDTO],
                     albums: Seq[AlbumDTO])

object SingerDTO {
  implicit val ByteArray: ScalarAlias[Array[Byte], String] = ScalarAlias[Array[Byte], String](
    StringType, _.map(_.toChar).mkString, bytea => Right(bytea.getBytes()))
  implicit val singerGraphQL = deriveObjectType[Unit, SingerDTO](
    ObjectTypeName("SingerDTO"),
    ReplaceField("photo", Field("photo", OptionType(ByteArray), resolve = _.value.photo)))
}
