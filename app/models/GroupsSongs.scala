package models

import slick.lifted.ProvenShape
import slick.jdbc.PostgresProfile.api._

case class GroupsSongs(groupId: Long, songId: Long)

class GroupsSongsTable(tag: Tag) extends Table[GroupsSongs](tag, "groups_songs") {
  def groupId = column[Long]("group_id")
  def songId = column[Long]("song_id")
  override def * : ProvenShape[GroupsSongs] = (groupId, songId) <> (GroupsSongs.tupled, GroupsSongs.unapply)
}

object GroupsSongsTable {
  lazy val groupsSongsTableQuery = TableQuery[GroupsSongsTable]
}