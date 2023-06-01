package repository.user

import db.Connection
import models.user.LikedSongsTable._
import models.user._
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

object LikedSongsRepository {
  val db = Connection.db
  def likeSong(likedSong: LikedSongs)= db.run(likedSongsTableQuery += likedSong)
  def deleteLikeSong(userId: Long, songId: Long) = db.run(likedSongsTableQuery.filter(_.songId === songId).filter(_.userId === userId).delete)
  def getLikedSongs(userId: Long): Future[Seq[LikedSongs]] = db.run(likedSongsTableQuery.filter(_.userId === userId).result)
  def getLikedSongById(userId: Long, songId: Long): Future[Option[LikedSongs]] = db.run(likedSongsTableQuery.filter(_.userId === userId).filter(_.songId === songId).result.headOption)

}
