package dto.histories

import sangria.macros.derive._

import java.time.LocalDate

case class HistorySongDTO(userId: Long, songId: Long)

object HistorySongDTO {
  implicit val historySongDTOGraphQL = deriveObjectType[Unit, HistorySongDTO](
    ObjectTypeName("HistorySongDTO")
  )

}
