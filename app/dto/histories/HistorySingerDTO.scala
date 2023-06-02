package dto.histories

import dto.SingerDTO
import models.Genres.Genre
import sangria.macros.derive.{ObjectTypeName, deriveObjectType}

case class HistorySingerDTO(userId: Long, singerDTO: SingerDTO, playingDate: String)

object HistorySingerDTO {
  implicit val historySingerDTOGraphQL = deriveObjectType[Unit, HistorySingerDTO](
    ObjectTypeName("HistorySingerDTO")
  )
}