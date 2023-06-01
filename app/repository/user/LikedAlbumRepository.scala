package repository.user

import db.Connection
import models.user.LikedAlbumsTable.likedAlbumsTableQuery
import models.user.LikedSongsTable._
import models.user._
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future
object LikedAlbumRepository {
  val db = Connection.db

  def likeAlbum(likedAlbum: LikedAlbums) = db.run(likedAlbumsTableQuery += likedAlbum)
  def deleteLikeAlbum(userId: Long, albumId: Long) = db.run(likedAlbumsTableQuery.filter(_.albumId === albumId).filter(_.userId === userId).delete)
  def getLikedAlbums(userId: Long): Future[Seq[LikedAlbums]] = db.run(likedAlbumsTableQuery.filter(_.userId === userId).result)
  def getLikedAlbumById(userId: Long, albumId: Long): Future[Option[LikedAlbums]] = db.run(likedAlbumsTableQuery.filter(_.userId === userId).filter(_.albumId === albumId).result.headOption)
}
