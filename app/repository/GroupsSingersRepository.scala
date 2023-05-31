package repository

import db.Connection
import models.GroupsSingers
import models.GroupsSingersTable._
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.Future
import scala.util.{Failure, Success}

object GroupsSingersRepository {

  import context.PrivateExecutionContext._

  val db = Connection.db

  def getAll(): Future[Seq[GroupsSingers]] = db.run(groupsSingersTableQuery.result)
  def getAllByGroupId(groupId: Long): Future[Seq[GroupsSingers]]  = db.run(groupsSingersTableQuery.filter(_.groupId === groupId).result)
  def add(q: GroupsSingers): Future[Int] = db.run(groupsSingersTableQuery += q)
  def delete(groupsSingers: GroupsSingers): Future[Int] = db.run(groupsSingersTableQuery.filter(_.groupId === groupsSingers.groupId).filter(_.singerId === groupsSingers.singerId).delete)
  def deleteAllByGroupId(id: Long): Future[Int] = db.run(groupsSingersTableQuery.filter(_.groupId === id).delete)
  def deleteAllBySingerId(id: Long): Future[Int] = db.run(groupsSingersTableQuery.filter(_.singerId === id).delete)
  def getById(groupId: Long, singerId:Long): Future[Option[GroupsSingers]] = db.run(groupsSingersTableQuery.filter(_.singerId === singerId).filter(_.groupId === groupId).result.headOption)

}

