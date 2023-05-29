package service

import dto.SingerDTO
import repository.{SingerRepository, SingersAlbumsRepository, SingersSongsRepository}
import service.GroupService.getById

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

class SingerService{
  def getById(id: Long) = SingerService.getById(id)
  def getAllById(ids: Seq[Long]) = SingerService.getAllById(ids)
  def getAll = SingerService.getAll()
}
object SingerService {

  def getById(id: Long) = {
    for {
      singer <- SingerRepository.getById(id)
      singersSongsIds <- SingersSongsRepository.getAllBySingerId(id)
      singersSongs <- SongService.getAllById(singersSongsIds.toList.map(_.songId))
      singersAlbumsIds <- SingersAlbumsRepository.getAllBySingerId(id)
      singersAlbums <- AlbumService.getAllById(singersAlbumsIds.toList.map(_.albumId))
      res = singer match {
        case Some(value) => {
          Option(SingerDTO(id = value.id, name = value.name, photo = value.photo, numOfPlays = value.numOfPlays, songs = singersSongs, albums = singersAlbums))
        }
        case None => None
      }
    } yield res
  }

  def getAllById(ids: Seq[Long]) = {
    for {
      singers <- SingerRepository.getAllById(ids.toList)
      singersIds = singers.map(singer => singer.id)
      singersDTO <- Future.sequence(singersIds.map(id => getById(id)))
      res = singersDTO.flatten
    } yield res
  }

  def getAll() = {
    for {
      singers <- SingerRepository.getAll()
      singersIds = singers.map(singer => singer.id)
      singersDTO <- Future.sequence(singersIds.map(id => getById(id)))
      res = singersDTO.flatten
    } yield res
  }
}
