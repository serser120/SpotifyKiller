package service

import dto.SongDTO
import repository._

import scala.concurrent.ExecutionContext.Implicits.global

class SongService{
  def getById(id: Long) = SongService.getById(id)
  def getAllById(ids: Seq[Long]) = SongService.getAllById(ids)
  def getAll = SongService.getAll()
}
object SongService {
  def getById(id: Long) = {
    for {
      song <- SongRepository.getById(id)
      res = song match {
        case Some(value) => {
          Option(SongDTO(id = value.id, name = value.name, photo = value.photo, length = value.length, song = value.song))
        }
        case None => None
      }
    } yield res
  }

  def getAllById(ids: Seq[Long]) = {
    for {
      songs <- SongRepository.getAllById(ids.toList)
      res = songs.map(value => SongDTO(id = value.id, name = value.name, photo = value.photo, length = value.length, song = value.song))
    } yield res
  }

  def getAll() = {
    for {
      songs <- SongRepository.getAll()
      res = songs.map(value => SongDTO(id = value.id, name = value.name, photo = value.photo, length = value.length, song = value.song))
    } yield res
  }



}
