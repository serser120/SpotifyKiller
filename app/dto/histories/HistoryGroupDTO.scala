package dto.histories

import dto.GroupDTO
import sangria.macros.derive.{ObjectTypeName, deriveObjectType}

case class HistoryGroupDTO(userId: Long, groupDTO: GroupDTO, playingDate: String)

object HistoryGroupDTO {
  implicit val historyGroupDTOGraphQL = deriveObjectType[Unit, HistoryGroupDTO](
    ObjectTypeName("HistoryGroupDTO")
  )
}