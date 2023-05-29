package dto

import dto.SongDTO.ByteArray
import sangria.macros.derive._
import sangria.schema._

case class GroupDTO(id: Long,
                    name: String,
                    photo: Array[Byte],
                    singers: Seq[SingerDTO],
                    numOfPlays: Long,
                    songs: Seq[SongDTO],
                    albums: Seq[AlbumDTO])

object GroupDTO {
  implicit val ByteArray: ScalarAlias[Array[Byte], String] = ScalarAlias[Array[Byte], String](
    StringType, _.map(_.toChar).mkString, bytea => Right(bytea.getBytes()))
  implicit val groupGraphQL = deriveObjectType[Unit, GroupDTO](
    ObjectTypeName("GroupDTO"),
    ReplaceField("photo", Field("photo", OptionType(ByteArray), resolve = _.value.photo)))
}
