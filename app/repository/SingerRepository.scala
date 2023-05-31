package repository

import db.Connection
import models.MusicianPerformer
import models.MusicianPerformerTable._
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

object SingerRepository {
  val db = Connection.db

  def getAll(): Future[Seq[MusicianPerformer]]  = db.run(singerTableQuery.result)
  def getAllById(ids: List[Long]): Future[Seq[MusicianPerformer]] = db.run(singerTableQuery.filter(_.id.inSet(ids)).result)
  def getById(id: Long): Future[Option[MusicianPerformer]] = db.run(singerTableQuery.filter(_.id === id).result.headOption)
  def add(singer: MusicianPerformer): Future[Int] = db.run(singerTableQuery += singer)
  def update(id: Long, singer: MusicianPerformer): Future[Int] = db.run(singerTableQuery.filter(_.id === id).update(singer))
  def delete(id: Long): Future[Int] = db.run(singerTableQuery.filter(_.id === id).delete)
  def findBySinger(numOfPlays: Long, name: String): Future[Option[MusicianPerformer]] = db.run(singerTableQuery.filter(_.numOfPlays === numOfPlays).filter (_.name.like(name)).result.headOption)
  def findByName(name: String): Future[Seq[MusicianPerformer]] = db.run(singerTableQuery.filter(_.name.like("%" + name + "%")).result)

}
