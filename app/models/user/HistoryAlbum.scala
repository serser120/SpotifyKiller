package models.user

import models.Genres.Genre
import models.MyEnumAPI

import java.time.LocalDate
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

case class HistoryGenre(userId: Long, genre: Genre, playingDate: LocalDate)
class HistoryGenreTable(tag: Tag) extends Table[HistoryGenre](tag, "genre_history") with MyEnumAPI {
  def userId = column[Long]("user_id")

  def genre = column[Genre]("genre")

  def playingDate = column[LocalDate]("playing_date")


  override def * : ProvenShape[HistoryGenre] = (userId, genre, playingDate) <> (HistoryGenre.tupled, HistoryGenre.unapply)
}
object HistoryGenreTable {
  lazy val historyGenreTableQuery = TableQuery[HistoryGenreTable]
}