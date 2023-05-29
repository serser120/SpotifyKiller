package repository

import db.Connection
import models.SingersSongs
import models.SingersSongsTable._
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.Future
import scala.util.{Failure, Success}

object SingersSongsRepository {

  import context.PrivateExecutionContext._

  val db = Connection.db

  def getAll(): Future[Seq[SingersSongs]] = db.run(singersSongsTableQuery.result)
  def getAllBySingerId(singerId: Long): Future[Seq[SingersSongs]] = db.run(singersSongsTableQuery.filter(_.singerId === singerId).result)
  def add(q: SingersSongs): Future[Int] = db.run(singersSongsTableQuery += q)
  def delete(q: SingersSongs): Future[Int] = db.run(singersSongsTableQuery.filter(_.singerId === q.singerId).filter(_.songId === q.songId).delete)
}