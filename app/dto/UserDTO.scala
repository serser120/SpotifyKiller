package dto

import models.user.Roles.Role
import sangria.macros.derive._
import sangria.schema._

case class UserDTO(id: Long,
                   login: String,
                   password: String,
                   email: String,
                   role: Role,
                   token: Long,
                   likedSongs: Seq[SongDTO],
                   likedAlbums: Seq[AlbumDTO],
                   likedSingers: Seq[SingerDTO],
                   likedGroups: Seq[GroupDTO])
//                   HistorySong: Seq[HistorySongDTO],
//                   HistoryGenre:Seq[HistoryGenreDTO],
//                   HistorySinger:Seq[HistorySingerDTO],
//                   HistoryGroup:Seq[HistoryGroupDTO])

object UserDTO {
  implicit val userGraphQL = deriveObjectType[Unit, UserDTO](
    ObjectTypeName("UserDTO")
  )
}
