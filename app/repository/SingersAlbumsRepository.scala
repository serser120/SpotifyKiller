package repository

import db.Connection
import models.SingersAlbums
import models.SingersAlbumsTable._
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.Future
import scala.util.{Failure, Success}

object SingersAlbumsRepository {

  import context.PrivateExecutionContext._

  val db = Connection.db

  def getAll(): Future[Seq[SingersAlbums]] = db.run(singersAlbumsTableQuery.result)
  def getAllBySingerId(singerId: Long): Future[Seq[SingersAlbums]] = db.run(singersAlbumsTableQuery.filter(_.singerId === singerId).result)
  def add(q: SingersAlbums): Future[Int] = db.run(singersAlbumsTableQuery += q)
  def delete(q: SingersAlbums): Future[Int] = db.run(singersAlbumsTableQuery.filter(_.singerId === q.singerId).filter(_.albumId === q.albumId).delete)
}


