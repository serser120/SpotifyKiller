package dto

import sangria.macros.derive._
import sangria.schema._


case class AlbumDTO(id: Long,
                    name: String,
                    photo: Array[Byte],
                    numOfPlays: Long,
                    songs: Seq[SongDTO]
                   )

object AlbumDTO {
  implicit val ByteArray: ScalarAlias[Array[Byte], String] = ScalarAlias[Array[Byte], String](
    StringType, _.map(_.toChar).mkString, bytea => Right(bytea.getBytes()))
  implicit val albumGraphQL = deriveObjectType[Unit, AlbumDTO](
    ObjectTypeName("AlbumDTO"),
    ReplaceField("photo", Field("photo", OptionType(ByteArray), resolve = _.value.photo)))
}