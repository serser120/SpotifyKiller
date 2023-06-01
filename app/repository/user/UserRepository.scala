package repository.user

import db.Connection
import models.user.LikedAlbumsTable._
import models.user.LikedGroupsTable._
import models.user.LikedSingersTable._
import models.user.LikedSongsTable._
import models.user.UserTable._
import models.user._
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future
object UserRepository {
  val db = Connection.db
  def getByLoginPassword(login: String, password: String): Future[Option[User]] = db.run(userTableQuery.filter(_.login.like(login)).filter(_.password.like(password)).result.headOption)
  def getByToken(token: Long): Future[Option[User]] = db.run(userTableQuery.filter(_.token === token).result.headOption)
  def registerUser(user: User): Future[Int] = db.run(userTableQuery += user)

}
