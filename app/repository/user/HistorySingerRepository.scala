package repository.user

import db.Connection
import models.user.HistorySinger
import models.user.HistorySingerTable._
import slick.jdbc.PostgresProfile.api._

import java.time.LocalDate
import scala.concurrent.Future

object HistorySingerRepository {
  val db = Connection.db
  def getAllByUserId(userId: Long): Future[Seq[HistorySinger]] = db.run(historySingerTableQuery.filter(_.userId === userId).result)
  def getAllByUserAndSinger(userId: Long, singerId: Long): Future[Seq[HistorySinger]] = db.run(historySingerTableQuery.filter(_.userId === userId).filter(_.singerId === singerId).result)
  def addHistory(userId: Long, singerId: Long) = db.run(historySingerTableQuery += HistorySinger(userId, singerId, LocalDate.now()))
}