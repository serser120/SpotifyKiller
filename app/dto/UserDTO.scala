package dto

import dto.histories.{HistoryGenreDTO, HistoryGroupDTO, HistorySingerDTO, HistorySongDTO}
import models.user.Roles.Role
import sangria.macros.derive._

case class UserDTO(id: Long,
                   login: String,
                   password: String,
                   email: String,
                   role: Role,
                   token: Long,
                   likedSongs: Seq[SongDTO],
                   likedAlbums: Seq[AlbumDTO],
                   likedSingers: Seq[SingerDTO],
                   likedGroups: Seq[GroupDTO],
                   historySong: Seq[HistorySongDTO],
                   historyGenre:Seq[HistoryGenreDTO],
                   historySinger:Seq[HistorySingerDTO],
                   historyGroup:Seq[HistoryGroupDTO])

object UserDTO {
  implicit val userGraphQL = deriveObjectType[Unit, UserDTO](
    ObjectTypeName("UserDTO")
  )
}
