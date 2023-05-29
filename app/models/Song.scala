package models

import slick.lifted.ProvenShape
import slick.jdbc.PostgresProfile.api._

case class Song(id: Long, name: String, photo: Array[Byte], length: Int, song: Array[Byte])

class SongTable(tag: Tag) extends Table[Song](tag, "songs") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def photo = column[Array[Byte]]("photo")

  def length = column[Int]("length")

  def song = column[Array[Byte]]("song")

  override def * : ProvenShape[Song] = (id, name, photo, length, song) <> (Song.tupled, Song.unapply)
}

object SongTable {
  lazy val songTableQuery = TableQuery[SongTable]
}
