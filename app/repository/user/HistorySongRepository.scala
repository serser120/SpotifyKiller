package repository.user

import db.Connection
import models.user.HistorySong
import models.user.HistorySongTable._
import slick.jdbc.PostgresProfile.api._
import java.time.LocalDate
import scala.concurrent.Future

object HistorySongRepository {

  val db = Connection.db
  def getAllByUserId(userId: Long): Future[Seq[HistorySong]] = db.run(historySongTableQuery.filter(_.userId === userId).result)
  def getAllByUserAndSong(userId: Long, songId: Long) = db.run(historySongTableQuery.filter(_.userId === userId).filter(_.songId === songId).result)
  def addHistory(userId: Long, songId: Long) = db.run(historySongTableQuery += HistorySong(userId, songId, LocalDate.now()))

}
