package models.user

import java.time.LocalDate
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape
case class HistoryGroup(userId: Long, groupId: Long, playingDate: LocalDate)
class HistoryGroupTable(tag: Tag) extends Table[HistoryGroup](tag, "group_history"){
  def userId = column[Long]("user_id")

  def groupId = column[Long]("group_id")

  def playingDate = column[LocalDate]("playing_date")


  override def * : ProvenShape[HistoryGroup] = (userId, groupId, playingDate) <> (HistoryGroup.tupled, HistoryGroup.unapply)
}
object HistoryGroupTable {
  lazy val historyGroupTableQuery = TableQuery[HistoryGroupTable]
}

