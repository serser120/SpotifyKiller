package models.user

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape
case class LikedGroups(userId: Long, groupId: Long)
class LikedGroupsTable(tag: Tag) extends Table[LikedGroups](tag, "liked_groups"){
  def userId = column[Long]("user_id")

  def groupId = column[Long]("group_id")

  override def * : ProvenShape[LikedGroups] = (userId, groupId) <> (LikedGroups.tupled, LikedGroups.unapply)
}
object LikedGroupsTable {
  lazy val likedGroupsTableQuery = TableQuery[LikedGroupsTable]
}