package service

import dto.SongDTO
import repository.{SongRepository, _}
import models.Song

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.impl.Promise

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

  def add(songDTO: SongDTO)= {
    for {
      temp <- SongRepository.add(Song(id = 0, name = songDTO.name, photo = songDTO.photo, length = songDTO.length, song = songDTO.song))
      finded <- SongRepository.findBySong(songDTO.length, songDTO.name)
      res = finded match {
        case Some(value) => {
          Option(SongDTO(id = value.id, name = value.name, photo = value.photo, length = value.length, song = value.song))
        }
        case None => None
      }
    } yield res
  }

  def update(id: Long, songDTO: SongDTO) = {
    for {
      temp <- SongRepository.update(id, Song(id = 0, name = songDTO.name, photo = songDTO.photo, length = songDTO.length, song = songDTO.song))
      finded <- SongRepository.findBySong(songDTO.length, songDTO.name)
      res = finded match {
        case Some(value) => {
          Option(SongDTO(id = value.id, name = value.name, photo = value.photo, length = value.length, song = value.song))
        }
        case None => None
      }
    } yield res
  }

  def delete(id: Long) = {
    for {
      answer <- SongRepository.delete(id)
      temp1 <- AlbumsSongsRepository.deleteAllBySongId(id)
      temp2 <- SingersSongsRepository.deleteAllBySongsId(id)
      temp3 <- GroupsSongsRepository.deleteAllBySongId(id)
      res = if(answer == 0) None else Option(id)
    } yield res
  }

}
