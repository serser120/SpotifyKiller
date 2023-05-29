package service

import dto.AlbumDTO
import repository.{AlbumRepository, AlbumsSongsRepository}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AlbumService{
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
}
