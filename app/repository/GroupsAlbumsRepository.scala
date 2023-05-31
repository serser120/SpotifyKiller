package repository


import db.Connection
import models.GroupsAlbums
import models.GroupsAlbumsTable._
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.Future
import scala.util.{Failure, Success}

object GroupsAlbumsRepository {

  import context.PrivateExecutionContext._

  val db = Connection.db

  def getAll(): Future[Seq[GroupsAlbums]] = db.run(groupsAlbumsTableQuery.result)
  def getAllByGroupId(groupId: Long): Future[Seq[GroupsAlbums]] = db.run(groupsAlbumsTableQuery.filter(_.groupId === groupId).result)
  def add(q: GroupsAlbums): Future[Int] = db.run(groupsAlbumsTableQuery += q)
  def delete(groupsAlbums: GroupsAlbums): Future[Int] = db.run(groupsAlbumsTableQuery.filter(_.groupId === groupsAlbums.groupId).filter(_.albumId === groupsAlbums.albumId).delete)
  def deleteAllByGroupId(id: Long): Future[Int] = db.run(groupsAlbumsTableQuery.filter(_.groupId === id).delete)
  def deleteAllByAlbumId(id: Long): Future[Int] = db.run(groupsAlbumsTableQuery.filter(_.albumId === id).delete)
  def getById(groupId: Long, albumId:Long): Future[Option[GroupsAlbums]] = db.run(groupsAlbumsTableQuery.filter(_.albumId === albumId).filter(_.groupId === groupId).result.headOption)

}
