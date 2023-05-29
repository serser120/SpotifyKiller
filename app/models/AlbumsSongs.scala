package models

import slick.lifted.ProvenShape
import slick.jdbc.PostgresProfile.api._

case class AlbumsSongs(albumId: Long, songId: Long)

class AlbumsSongsTable(tag: Tag) extends Table[AlbumsSongs](tag, "albums_songs") {
  def albumId = column[Long]("album_id")
  def songId = column[Long]("song_id")
  override def * : ProvenShape[AlbumsSongs] = (albumId,songId) <> (AlbumsSongs.tupled, AlbumsSongs.unapply)
}

object AlbumsSongsTable {
  lazy val albumsSongsTableQuery = TableQuery[AlbumsSongsTable]
}