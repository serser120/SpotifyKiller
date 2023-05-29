package repository

import db.Connection
import models.MusicianPerformer
import models.MusicianPerformerTable._
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.Future
import scala.util.{Failure, Success}

object GroupRepository {
  import context.PrivateExecutionContext._
  val db = Connection.db

  def getAll(): Future[Seq[MusicianPerformer]] = db.run(groupTableQuery.result)
  def getAllById(ids: List[Long]): Future[Seq[MusicianPerformer]] = db.run(groupTableQuery.filter(_.id.inSet(ids)).result)
  def getById(id: Long): Future[Option[MusicianPerformer]] = db.run(groupTableQuery.filter(_.id === id).result.headOption)
  def add(group: MusicianPerformer): Future[Int] = db.run(groupTableQuery += group)
  def update(id: Long, group: MusicianPerformer): Future[Int] = db.run(groupTableQuery.filter(_.id === id).update(group))
  def delete(id: Long): Future[Int] = db.run(groupTableQuery.filter(_.id === id).delete)
}
