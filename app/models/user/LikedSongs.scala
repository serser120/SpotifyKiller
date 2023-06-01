package models.user

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape
case class LikedSongs(userId: Long, songId: Long)
class LikedSongsTable(tag: Tag) extends Table[LikedSongs](tag, "liked_songs"){
  def userId = column[Long]("user_id")

  def songId = column[Long]("song_id")

  override def * : ProvenShape[LikedSongs] = (userId, songId) <> (LikedSongs.tupled, LikedSongs.unapply)
}
object LikedSongsTable {
  lazy val likedSongsTableQuery = TableQuery[LikedSongsTable]
}
