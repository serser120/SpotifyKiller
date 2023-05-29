package repository

import db.Connection
import models.AlbumsSongs
import models.AlbumsSongsTable._
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.Future
import scala.util.{Failure, Success}

object AlbumsSongsRepository {

  import context.PrivateExecutionContext._

  val db = Connection.db

  def getAll(): Future[Seq[AlbumsSongs]] = db.run(albumsSongsTableQuery.result)
  def getAllByAlbumId(albumId: Long): Future[Seq[AlbumsSongs]] = db.run(albumsSongsTableQuery.filter(_.albumId === albumId).result)
  def add(q: AlbumsSongs): Future[Int] = db.run(albumsSongsTableQuery += q)
  def delete(albumsSongs: AlbumsSongs): Future[Int] = db.run(albumsSongsTableQuery.filter(_.songId === albumsSongs.songId).filter(_.albumId === albumsSongs.albumId).delete)
}
