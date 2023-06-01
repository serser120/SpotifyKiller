package models.user

import java.time.LocalDate
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

case class HistorySinger(userId: Long, singerId: Long, playingDate: LocalDate)
class HistorySingerTable(tag: Tag) extends Table[HistorySinger](tag, "singer_history"){
  def userId = column[Long]("user_id")

  def singerId = column[Long]("singer_id")

  def playingDate = column[LocalDate]("playing_date")


  override def * : ProvenShape[HistorySinger] = (userId, singerId, playingDate) <> (HistorySinger.tupled, HistorySinger.unapply)
}
object HistorySingerTable {
  lazy val historySingerTableQuery = TableQuery[HistorySingerTable]
}

