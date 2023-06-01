package models.user

import models.MyEnumAPI
import models.user.Roles.{Role, user}
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

case class User(id: Long, login: String, password: String, email: String, role: Role, token: Long)

class UserTable(tag: Tag) extends Table[User](tag, "users") with MyEnumAPI {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def login = column[String]("login")
  def password = column[String]("password")
  def email = column[String]("email")
  def role = column[Role]("role", O.Default(user))
  def token = column[Long]("token")

  override def * : ProvenShape[User] = (id, login, password, email, role, token) <> (User.tupled, User.unapply)
}
object UserTable {
  lazy val userTableQuery = TableQuery[UserTable]
}