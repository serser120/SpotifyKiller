package models.user

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape
case class LikedAlbums(userId: Long, albumId: Long)
class LikedAlbumsTable(tag: Tag) extends Table[LikedAlbums](tag, "liked_albums"){
  def userId = column[Long]("user_id")

  def albumId = column[Long]("album_id")

  override def * : ProvenShape[LikedAlbums] = (userId, albumId) <> (LikedAlbums.tupled, LikedAlbums.unapply)
}
object LikedAlbumsTable {
  lazy val likedAlbumsTableQuery = TableQuery[LikedAlbumsTable]
}
