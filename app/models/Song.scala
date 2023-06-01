package models

import models.Genres.{Genre, Jazz}
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape


case class Song(id: Long, name: String, photo: Array[Byte], length: Int, song: Array[Byte], genre: Genre)

class SongTable(tag: Tag) extends Table[Song](tag, "songs") with MyEnumAPI {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def photo = column[Array[Byte]]("photo")

  def length = column[Int]("length")

  def song = column[Array[Byte]]("song")
  def genre = column[Genre]("genre", O.Default(Jazz))

  override def * : ProvenShape[Song] = (id, name, photo, length, song, genre) <> (Song.tupled, Song.unapply)
}



object SongTable {
  lazy val songTableQuery = TableQuery[SongTable]
}
