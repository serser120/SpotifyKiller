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

//  def add(albumDTO: AlbumDTO) = {
//    for {
//      temp1 <- AlbumRepository.add(MusicianPerformer(id = 0, name = albumDTO.name, photo = albumDTO.photo, numOfPlays = albumDTO.numOfPlays))
//      finded <- AlbumRepository.findByAlbum(albumDTO.numOfPlays, albumDTO.name)
//      id = finded match {
//        case Some(value) => value.id
//        case None => 0L
//      }
//      songsIds = albumDTO.songs.map(_.id)
//      temp2 = songsIds.map(songId => AlbumsSongsRepository.add(AlbumsSongs(albumId = id, songId = songId)))
//      res <- getById(id)
//    } yield res
//  }

}
