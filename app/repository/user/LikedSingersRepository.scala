package repository.user

import db.Connection
import models.user.LikedSingersTable.likedSingersTableQuery
import models.user._
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future
object LikedSingersRepository {
  val db = Connection.db

  def likeSinger(likedSinger: LikedSingers) = db.run(likedSingersTableQuery += likedSinger)
  def deleteLikeSinger(userId: Long, singerId: Long) = db.run(likedSingersTableQuery.filter(_.singerId === singerId).filter(_.userId === userId).delete)
  def getLikedSingers(userId: Long): Future[Seq[LikedSingers]] = db.run(likedSingersTableQuery.filter(_.userId === userId).result)

  def getLikedSingerById(userId: Long, singerId: Long): Future[Option[LikedSingers]] = db.run(likedSingersTableQuery.filter(_.userId === userId).filter(_.singerId === singerId).result.headOption)

}
