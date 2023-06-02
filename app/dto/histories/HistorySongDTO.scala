package dto.histories

import dto.SongDTO
import sangria.macros.derive._

import java.time.LocalDate

case class HistorySongDTO(userId: Long, songDTO: SongDTO, playingDate: String)

object HistorySongDTO {
  implicit val historySongDTOGraphQL = deriveObjectType[Unit, HistorySongDTO](
    ObjectTypeName("HistorySongDTO")
  )

}
