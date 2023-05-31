package repository

import db.Connection
import models.MusicianPerformer
import models.MusicianPerformerTable._
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

object GroupRepository {
  val db = Connection.db

  def getAll(): Future[Seq[MusicianPerformer]] = db.run(groupTableQuery.result)
  def getAllById(ids: List[Long]): Future[Seq[MusicianPerformer]] = db.run(groupTableQuery.filter(_.id.inSet(ids)).result)
  def getById(id: Long): Future[Option[MusicianPerformer]] = db.run(groupTableQuery.filter(_.id === id).result.headOption)
  def add(group: MusicianPerformer): Future[Int] = db.run(groupTableQuery += group)
  def update(id: Long, group: MusicianPerformer): Future[Int] = db.run(groupTableQuery.filter(_.id === id).update(group))
  def delete(id: Long): Future[Int] = db.run(groupTableQuery.filter(_.id === id).delete)
  def findByGroup(numOfPlays: Long, name: String): Future[Option[MusicianPerformer]] = db.run(groupTableQuery.filter(_.numOfPlays === numOfPlays).filter (_.name.like(name)).result.headOption)
  def findByName(name: String): Future[Seq[MusicianPerformer]] = db.run(groupTableQuery.filter(_.name.like("%" + name + "%")).result)

}
