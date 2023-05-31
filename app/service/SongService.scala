package service

import dto.SongDTO
import repository.{SongRepository, _}
import models._
import service.IdsValidator

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.impl.Promise

class SongService {
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

  def add(name: String, photo: Array[Byte], length: Int, song: Array[Byte]) = {
    for {
      temp <- SongRepository.add(Song(id = 0, name = name, photo = photo, length = length, song = song))
      finded <- SongRepository.findBySong(length, name)
      res = finded match {
        case Some(value) => {
          Option(SongDTO(id = value.id, name = value.name, photo = value.photo, length = value.length, song = value.song))
        }
        case None => None
      }
    } yield res
  }

  def update(id: Long, name: String, photo: Array[Byte], length: Int, song: Array[Byte]) = {
    for {
      songFlag <- IdsValidator.songIdValidate(id)
      res = if (songFlag) {
        SongRepository.update(id, Song(id = id, name = name, photo = photo, length = length, song = song))
        "Success"
      } else {
        "Cant update this id"
      }
    } yield res
  }

  def delete(id: Long) = {
    for {
      songFlag <- IdsValidator.songIdValidate(id)
      res = if (songFlag) {
        SongRepository.delete(id)
        AlbumsSongsRepository.deleteAllBySongId(id)
        SingersSongsRepository.deleteAllBySongsId(id)
        GroupsSongsRepository.deleteAllBySongId(id)
        "Success"
      } else {
        "Cant delete this id"
      }
    } yield res
  }

  def addSongToAlbum(songId: Long, albumId: Long) = {
    for {
      songFlag <- IdsValidator.songIdValidate(songId)
      albumFlag <- IdsValidator.albumIdValidate(songId)
      albumsSongsFlag <- IdsValidator.albumsSongsIdValidate(albumId, songId)
      res = if (songFlag && albumFlag && !albumsSongsFlag) {
        AlbumsSongsRepository.add(AlbumsSongs(albumId, songId))
        "Success"
      } else {
        "Cant add this ids"
      }
    } yield res
  }

}
