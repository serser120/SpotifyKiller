package repository.user

import db.Connection
import models.user.HistoryGroup
import models.user.HistoryGroupTable._
import slick.jdbc.PostgresProfile.api._
import java.time.LocalDate
import scala.concurrent.Future

object HistoryGroupRepository {
  val db = Connection.db
  def getAllByUserId(userId: Long): Future[Seq[HistoryGroup]] = db.run(historyGroupTableQuery.filter(_.userId === userId).result)
  def getAllByUserAndGroup(userId: Long, groupId: Long): Future[Seq[HistoryGroup]] = db.run(historyGroupTableQuery.filter(_.userId === userId).filter(_.groupId === groupId).result)
  def addHistory(userId: Long, groupId: Long) = db.run(historyGroupTableQuery += HistoryGroup(userId, groupId, LocalDate.now()))
}