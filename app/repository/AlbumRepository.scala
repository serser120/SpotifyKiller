package repository

import db.Connection
import models.MusicianPerformer
import models.MusicianPerformerTable._
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

object AlbumRepository {

  import context.PrivateExecutionContext._

  val db = Connection.db

  def getAll(): Future[Seq[MusicianPerformer]] = db.run(albumTableQuery.result)
  def getAllById(ids: List[Long]): Future[Seq[MusicianPerformer]] = db.run(albumTableQuery.filter(_.id.inSet(ids)).result)
  def getById(id: Long): Future[Option[MusicianPerformer]] = db.run(albumTableQuery.filter(_.id === id).result.headOption)
  def add(album: MusicianPerformer): Future[Int] = db.run(albumTableQuery += album)
  def update(id: Long, album: MusicianPerformer): Future[Int] = db.run(albumTableQuery.filter(_.id === id).update(album))
  def delete(id: Long): Future[Int] = db.run(albumTableQuery.filter(_.id === id).delete)
  def findByAlbum(numOfPlays: Long, name: String): Future[Option[MusicianPerformer]] = db.run(albumTableQuery.filter(_.numOfPlays === numOfPlays).filter (_.name.like(name)).result.headOption)

}
