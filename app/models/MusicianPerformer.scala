package models

import slick.lifted.ProvenShape
import slick.jdbc.PostgresProfile.api._


case class MusicianPerformer(id: Long, name: String, photo: Array[Byte], numOfPlays: Long)

class SingerTable(tag: Tag) extends Table[MusicianPerformer](tag, "singers") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def photo = column[Array[Byte]]("photo")

  def numOfPlays = column[Long]("number_of_plays")

  override def * : ProvenShape[MusicianPerformer] = (id, name, photo, numOfPlays) <> (MusicianPerformer.tupled, MusicianPerformer.unapply)
}

class GroupTable(tag: Tag) extends Table[MusicianPerformer](tag, "groups") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def photo = column[Array[Byte]]("photo")

  def numOfPlays = column[Long]("number_of_plays")

  override def * : ProvenShape[MusicianPerformer] = (id, name, photo, numOfPlays) <> (MusicianPerformer.tupled, MusicianPerformer.unapply)
}

class AlbumTable(tag: Tag) extends Table[MusicianPerformer](tag, "albums") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def photo = column[Array[Byte]]("photo")

  def numOfPlays = column[Long]("number_of_plays")

  override def * : ProvenShape[MusicianPerformer] = (id, name, photo, numOfPlays) <> (MusicianPerformer.tupled, MusicianPerformer.unapply)
}

object MusicianPerformerTable {
  lazy val singerTableQuery = TableQuery[SingerTable]
  lazy val groupTableQuery = TableQuery[GroupTable]
  lazy val albumTableQuery = TableQuery[AlbumTable]
}
