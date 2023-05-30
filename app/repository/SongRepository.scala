package repository

import db.Connection
import models.SongTable.songTableQuery
import models._
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

object SongRepository {

  val db = Connection.db
  def getAll(): Future[Seq[Song]] = db.run(songTableQuery.result)
  def getAllById(ids: List[Long]): Future[Seq[Song]] = db.run(songTableQuery.filter(_.id.inSet(ids)).result)
  def getById(id: Long): Future[Option[Song]] = db.run(songTableQuery.filter(_.id === id).result.headOption)
  def add(song: Song): Future[Int] = db.run(songTableQuery += song)
  def update(id: Long, song: Song): Future[Int] = db.run(songTableQuery.filter(_.id === id).update(song))
  def delete(id: Long)= db.run(songTableQuery.filter(_.id === id).delete)
  def findBySong(length: Int, name: String): Future[Option[Song]] = db.run(songTableQuery.filter(_.length === length).filter (_.name.like(name)).result.headOption)
}
