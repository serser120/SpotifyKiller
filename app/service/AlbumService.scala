package service

import dto.AlbumDTO
import models._
import repository.{AlbumRepository, AlbumsSongsRepository, GroupsAlbumsRepository, SingersAlbumsRepository}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.reflect.ClassManifestFactory.Nothing

class AlbumService {
  def getById(id: Long) = AlbumService.getById(id)

  def getAllById(ids: Seq[Long]) = AlbumService.getAllById(ids)

  def getAll = AlbumService.getAll()
}

object AlbumService {

  def getById(id: Long) = {
    for {
      album <- AlbumRepository.getById(id)
      albumSongsIds <- AlbumsSongsRepository.getAllByAlbumId(id)
      albumSongs <- SongService.getAllById(albumSongsIds.toList.map(_.songId))
      res = album match {
        case Some(value) => {
          Option(AlbumDTO(id = value.id, name = value.name, photo = value.photo, numOfPlays = value.numOfPlays, songs = albumSongs))
        }
        case None => None
      }
    } yield res
  }

  def getAllById(ids: Seq[Long]) = {
    for {
      albums <- AlbumRepository.getAllById(ids.toList)
      albumsIds = albums.map(album => album.id)
      albumsDTO <- Future.sequence(albumsIds.map(id => getById(id)))
      res = albumsDTO.flatten
    } yield res
  }

  def getAll() = {
    for {
      albums <- AlbumRepository.getAll()
      albumsIds = albums.map(album => album.id)
      albumsDTO <- Future.sequence(albumsIds.map(id => getById(id)))
      res = albumsDTO.flatten
    } yield res
  }

  def add(name: String, photo: Array[Byte], numOfPlays: Long) = {
    for {
      temp1 <- AlbumRepository.add(MusicianPerformer(id = 0, name = name, photo = photo, numOfPlays = numOfPlays))
      finded <- AlbumRepository.findByAlbum(numOfPlays, name)
      id = finded match {
        case Some(value) => value.id
        case None => 0L
      }
      res <- AlbumService.getById(id)
    } yield res
  }

  def update(id: Long, name: String, photo: Array[Byte], numOfPlays: Long) = {
    for {
      temp <- AlbumRepository.update(id, MusicianPerformer(id = id, name = name, photo = photo, numOfPlays = numOfPlays))
      res <- AlbumService.getById(id)
    } yield res
  }

  def delete(id: Long) = {
    for {
      answer <- AlbumRepository.delete(id)
      temp1 <- AlbumsSongsRepository.deleteAllByAlbumId(id)
      temp2 <- SingersAlbumsRepository.deleteAllByAlbumId(id)
      temp3 <- GroupsAlbumsRepository.deleteAllByAlbumId(id)
      res = if (answer == 0) None else Option(id)
    } yield res
  }

}
