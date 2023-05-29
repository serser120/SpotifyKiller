package repository

import db.Connection
import models.GroupsSongs
import models.GroupsSongsTable._
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.Future
import scala.util.{Failure, Success}

object GroupsSongsRepository {

  import context.PrivateExecutionContext._

  val db = Connection.db

  def getAll(): Future[Seq[GroupsSongs]] = db.run(groupsSongsTableQuery.result)
  def getAllByGroupId(groupId: Long): Future[Seq[GroupsSongs]] = db.run(groupsSongsTableQuery.filter(_.groupId === groupId).result)
  def add(q: GroupsSongs): Future[Int]= db.run(groupsSongsTableQuery += q)
  def delete(groupsSongs: GroupsSongs): Future[Int] = db.run(groupsSongsTableQuery.filter(_.groupId === groupsSongs.groupId).filter(_.songId === groupsSongs.songId).delete)
}


