package models

import slick.lifted.ProvenShape
import slick.jdbc.PostgresProfile.api._

case class SingersAlbums(singerId: Long, albumId: Long)

class SingersAlbumsTable(tag: Tag) extends Table[SingersAlbums](tag, "singers_albums") {
  def singerId = column[Long]("singer_id")
  def albumId = column[Long]("album_id")
  override def * : ProvenShape[SingersAlbums] = (singerId, albumId) <> (SingersAlbums.tupled, SingersAlbums.unapply)
}

object SingersAlbumsTable {
  lazy val singersAlbumsTableQuery = TableQuery[SingersAlbumsTable]
}
