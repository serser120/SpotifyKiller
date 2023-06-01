package repository.user

import db.Connection
import models.user.LikedGroupsTable.likedGroupsTableQuery
import models.user.LikedSingersTable.likedSingersTableQuery
import models.user._
import repository.user.LikedSingersRepository.db
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future
object LikedGroupRepository {
  val db = Connection.db

  def likeGroup(likedGroup: LikedGroups) = db.run(likedGroupsTableQuery += likedGroup)
  def deleteLikeGroup(userId: Long, groupId: Long) = db.run(likedGroupsTableQuery.filter(_.groupId === groupId).filter(_.userId === userId).delete)
  def getLikedGroups(userId: Long): Future[Seq[LikedGroups]] = db.run(likedGroupsTableQuery.filter(_.userId === userId).result)
  def getLikedGroupById(userId: Long, groupId: Long): Future[Option[LikedGroups]] = db.run(likedGroupsTableQuery.filter(_.userId === userId).filter(_.groupId === groupId).result.headOption)


}
