package models.user

import java.time.LocalDate
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

case class HistorySong(userId: Long, songId: Long, playingDate: LocalDate)
class HistorySongTable(tag: Tag) extends Table[HistorySong](tag, "song_history"){
  def userId = column[Long]("user_id")

  def songId = column[Long]("song_id")

  def playingDate = column[LocalDate]("playing_date")


  override def * : ProvenShape[HistorySong] = (userId, songId, playingDate) <> (HistorySong.tupled, HistorySong.unapply)
}
object HistorySongTable {
  lazy val historySongTableQuery = TableQuery[HistorySongTable]
}
