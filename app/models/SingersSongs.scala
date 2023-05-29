package models

import slick.lifted.ProvenShape
import slick.jdbc.PostgresProfile.api._

case class SingersSongs(singerId: Long, songId: Long)

class SingersSongsTable(tag: Tag) extends Table[SingersSongs](tag, "singers_songs") {
  def singerId = column[Long]("singer_id")
  def songId = column[Long]("song_id")
  override def * : ProvenShape[SingersSongs] = (singerId, songId) <> (SingersSongs.tupled, SingersSongs.unapply)
}

object SingersSongsTable {
  lazy val singersSongsTableQuery = TableQuery[SingersSongsTable]
}
