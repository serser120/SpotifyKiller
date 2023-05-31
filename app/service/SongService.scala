package service

import dto.SongDTO
import models._
import models.Genres.Genre
import repository._

import scala.concurrent.ExecutionContext.Implicits.global

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
          Option(SongDTO(id = value.id, name = value.name, photo = value.photo, length = value.length, song = value.song, genre = value.genre))
        }
        case None => None
      }
    } yield res
  }

  def getAllById(ids: Seq[Long]) = {
    for {
      songs <- SongRepository.getAllById(ids.toList)
      res = songs.map(value => SongDTO(id = value.id, name = value.name, photo = value.photo, length = value.length, song = value.song, genre = value.genre))
    } yield res
  }

  def getAll() = {
    for {
      songs <- SongRepository.getAll()
      res = songs.map(value => SongDTO(id = value.id, name = value.name, photo = value.photo, length = value.length, song = value.song, genre = value.genre))
    } yield res
  }

  def add(name: String, photo: Array[Byte], length: Int, song: Array[Byte], genre: Genre) = {
    for {
      temp <- SongRepository.add(Song(id = 0, name = name, photo = photo, length = length, song = song, genre = genre))
      finded <- SongRepository.findBySong(length, name)
      res = finded match {
        case Some(value) => {
          Option(SongDTO(id = value.id, name = value.name, photo = value.photo, length = value.length, song = value.song, genre = value.genre))
        }
        case None => None
      }
    } yield res
  }

  def update(id: Long, name: String, photo: Array[Byte], length: Int, song: Array[Byte], genre: Genre) = {
    for {
      songFlag <- IdsValidator.songIdValidate(id)
      res = if (songFlag) {
        SongRepository.update(id, Song(id = id, name = name, photo = photo, length = length, song = song, genre = genre))
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
      albumFlag <- IdsValidator.albumIdValidate(albumId)
      albumsSongsFlag <- IdsValidator.albumsSongsIdValidate(albumId, songId)
      res = if (songFlag && albumFlag && !albumsSongsFlag) {
        AlbumsSongsRepository.add(AlbumsSongs(albumId, songId))
        "Success"
      } else {
        "Cant add this ids"
      }
    } yield res
  }

  def deleteSongFromAlbum(songId: Long, albumId: Long) = {
    for {
      songFlag <- IdsValidator.songIdValidate(songId)
      albumFlag <- IdsValidator.albumIdValidate(albumId)
      albumsSongsFlag <- IdsValidator.albumsSongsIdValidate(albumId, songId)
      res = if (songFlag && albumFlag && albumsSongsFlag) {
        AlbumsSongsRepository.delete(AlbumsSongs(albumId, songId))
        "Success"
      } else {
        "Cant delete this ids"
      }
    } yield res
  }

  def addSongToSinger(songId: Long, singerId: Long) = {
    for {
      songFlag <- IdsValidator.songIdValidate(songId)
      singerFlag <- IdsValidator.singerIdValidate(singerId)
      singersSongsFlag <- IdsValidator.singersSongsIdValidate(singerId, songId)
      res = if (songFlag && singerFlag && !singersSongsFlag) {
        SingersSongsRepository.add(SingersSongs(singerId, songId))
        "Success"
      } else {
        "Cant add this ids"
      }
    } yield res
  }

  def deleteSongFromSinger(songId: Long, singerId: Long) = {
    for {
      songFlag <- IdsValidator.songIdValidate(songId)
      singerFlag <- IdsValidator.singerIdValidate(singerId)
      singersSongsFlag <- IdsValidator.singersSongsIdValidate(singerId, songId)
      res = if (songFlag && singerFlag && singersSongsFlag) {
        SingersSongsRepository.delete(SingersSongs(singerId, songId))
        "Success"
      } else {
        "Cant delete this ids"
      }
    } yield res
  }

  def addSongToGroup(songId: Long, groupId: Long) = {
    for {
      songFlag <- IdsValidator.songIdValidate(songId)
      groupFlag <- IdsValidator.groupIdValidate(groupId)
      groupsSongsFlag <- IdsValidator.groupsSongsIdValidate(groupId, songId)
      res = if (songFlag && groupFlag && !groupsSongsFlag) {
        GroupsSongsRepository.add(GroupsSongs(groupId, songId))
        "Success"
      } else {
        "Cant add this ids"
      }
    } yield res
  }

  def deleteSongFromGroup(songId: Long, groupId: Long) = {
    for {
      songFlag <- IdsValidator.songIdValidate(songId)
      groupFlag <- IdsValidator.groupIdValidate(groupId)
      groupsSongsFlag <- IdsValidator.groupsSongsIdValidate(groupId, songId)
      res = if (songFlag && groupFlag && groupsSongsFlag) {
        GroupsSongsRepository.delete(GroupsSongs(groupId, songId))
        "Success"
      } else {
        "Cant delete this ids"
      }
    } yield res
  }

  def getByName(name: String) = {
    for {
      songs <- SongRepository.findByName(name)
      res = songs.map(value => SongDTO(id = value.id, name = value.name, photo = value.photo, length = value.length, song = value.song, genre = value.genre))
    } yield res
  }
}
