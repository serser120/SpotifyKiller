package repository.user

import db.Connection
import models.Genres.Genre
import models.user.HistoryGenre
import models.user.HistoryGenreTable._
import slick.jdbc.PostgresProfile.api._

import java.time.LocalDate
import scala.concurrent.Future

object HistoryGenreRepository {
  val db = Connection.db
  def getAllByUserId(userId: Long): Future[Seq[HistoryGenre]] = db.run(historyGenreTableQuery.filter(_.userId === userId).result)
  def getAllByUserAndGenre(userId: Long, genre: Genre): Future[Seq[HistoryGenre]] = db.run(historyGenreTableQuery.filter(_.userId === userId).filter(_.genre.toString().equals(genre.toString)).result)
  def addHistory(userId: Long, genre: Genre) = db.run(historyGenreTableQuery += HistoryGenre(userId, genre, LocalDate.now()))
}