package dto.histories

import models.Genres.Genre
import sangria.macros.derive.{ObjectTypeName, deriveObjectType}

case class HistoryGenreDTO(userId: Long, genre: Genre, playingDate: String)

object HistoryGenreDTO {
  implicit val historyGenreDTOGraphQL = deriveObjectType[Unit, HistoryGenreDTO](
    ObjectTypeName("HistoryGenreDTO")
  )
}