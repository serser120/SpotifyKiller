package models

import slick.lifted.ProvenShape
import slick.jdbc.PostgresProfile.api._

case class GroupsSingers(groupId: Long, singerId: Long)

class GroupsSingersTable(tag: Tag) extends Table[GroupsSingers](tag, "groups_singers") {
  def groupId = column[Long]("group_id")
  def singerId = column[Long]("singer_id")
  override def * : ProvenShape[GroupsSingers] = (groupId, singerId) <> (GroupsSingers.tupled, GroupsSingers.unapply)
}

object GroupsSingersTable {
  lazy val groupsSingersTableQuery = TableQuery[GroupsSingersTable]
}