package repository

import db.Connection
import models._
import models.SongTable.songTableQuery
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

object SongRepository {

  import context.PrivateExecutionContext._

  val db = Connection.db
  def getAll(): Future[Seq[Song]] = db.run(songTableQuery.result)
  def getAllById(ids: List[Long]): Future[Seq[Song]] = db.run(songTableQuery.filter(_.id.inSet(ids)).result)
  def getById(id: Long): Future[Option[Song]] = db.run(songTableQuery.filter(_.id === id).result.headOption)
  def add(song: Song): Future[Int] = db.run(songTableQuery += song)
  def update(id: Long, song: Song): Future[Int] = db.run(songTableQuery.filter(_.id === id).update(song))
  def delete(id: Long): Future[Int] =  db.run(songTableQuery.filter(_.id === id).delete)
}
