package models.user

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape
case class LikedSingers(userId: Long, singerId: Long)
class LikedSingersTable(tag: Tag) extends Table[LikedSingers](tag, "liked_singers"){
  def userId = column[Long]("user_id")

  def singerId = column[Long]("singer_id")

  override def * : ProvenShape[LikedSingers] = (userId, singerId) <> (LikedSingers.tupled, LikedSingers.unapply)
}
object LikedSingersTable {
  lazy val likedSingersTableQuery = TableQuery[LikedSingersTable]
}