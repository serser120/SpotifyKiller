package models

import slick.lifted.ProvenShape
import slick.jdbc.PostgresProfile.api._

case class GroupsAlbums(groupId: Long, albumId: Long)

class GroupsAlbumsTable(tag: Tag) extends Table[GroupsAlbums](tag, "groups_albums") {
  def groupId = column[Long]("group_id")
  def albumId = column[Long]("album_id")
  override def * : ProvenShape[GroupsAlbums] = (groupId, albumId) <> (GroupsAlbums.tupled, GroupsAlbums.unapply)
}

object GroupsAlbumsTable {
  lazy val groupsAlbumsTableQuery = TableQuery[GroupsAlbumsTable]
}