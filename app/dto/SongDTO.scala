package dto

import models.Genres
import sangria.macros.derive._
import sangria.schema._
import models.Genres.Genre

case class SongDTO(id: Long,
                   name: String,
                   photo: Array[Byte],
                   length: Int,
                   song: Array[Byte],
                   genre: Genre)

object SongDTO {
  implicit val ByteArray: ScalarAlias[Array[Byte], String] = ScalarAlias[Array[Byte], String](
    StringType, _.map(_.toChar).mkString, bytea => Right(bytea.getBytes()))
  implicit val songGraphQL = deriveObjectType[Unit, SongDTO](
    ObjectTypeName("SongDTO"),
    ReplaceField("photo", Field("photo", OptionType(ByteArray), resolve = _.value.photo)),
    ReplaceField("song", Field("song", OptionType(ByteArray), resolve = _.value.song))
//    ReplaceField("genre", Field("genre", EnumType(GenreType), resolve = _.value.genre))
  )


}