package service

import dto.SingerDTO
import repository.{GroupsSingersRepository, SingerRepository, SingersAlbumsRepository, SingersSongsRepository}
import models._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

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

  def add(name: String, photo: Array[Byte], numOfPlays: Long) = {
    for {
      temp1 <- SingerRepository.add(MusicianPerformer(id = 0, name = name, photo = photo, numOfPlays = numOfPlays))
      finded <- SingerRepository.findBySinger(numOfPlays, name)
      id = finded match {
        case Some(value) => value.id
        case None => 0L
      }
      res <- SingerService.getById(id)
    } yield res
  }

  def update(id: Long, name: String, photo: Array[Byte], numOfPlays: Long) = {
    for {
      temp <- SingerRepository.update(id, MusicianPerformer(id = id, name = name, photo = photo, numOfPlays = numOfPlays))
      res <- SingerService.getById(id)
    } yield res
  }

  def delete(id: Long) = {
    for {
      answer <- SingerRepository.delete(id)
      temp1 <- SingersSongsRepository.deleteAllBySingerId(id)
      temp2 <- SingersAlbumsRepository.deleteAllBySingerId(id)
      temp3 <- GroupsSingersRepository.deleteAllBySingerId(id)
      res = if (answer == 0) None else Option(id)
    } yield res
  }

  def addSingerToGroup(singerId: Long, groupId: Long) = {
    for {
      singerFlag <- IdsValidator.albumIdValidate(singerId)
      groupFlag <- IdsValidator.groupIdValidate(groupId)
      singersGroupsFlag <- IdsValidator.groupsSingersIdValidate(groupId, singerId)
      res = if (singerFlag && groupFlag && !singersGroupsFlag) {
        GroupsSingersRepository.add(GroupsSingers(groupId, singerId))
        "Success"
      } else {
        "Cant add this ids"
      }
    } yield res
  }

  def deleteSingerFromGroup(singerId: Long, groupId: Long) = {
    for {
      singerFlag <- IdsValidator.albumIdValidate(singerId)
      groupFlag <- IdsValidator.groupIdValidate(groupId)
      singersGroupsFlag <- IdsValidator.groupsSingersIdValidate(groupId, singerId)
      res = if (singerFlag && groupFlag && singersGroupsFlag) {
        GroupsSingersRepository.delete(GroupsSingers(groupId, singerId))
        "Success"
      } else {
        "Cant delete this ids"
      }
    } yield res
  }

  def getByName(name: String) = {
    for {
      singers <- SingerRepository.findByName(name)
      singersIds = singers.map(singer => singer.id)
      singersDTO <- Future.sequence(singersIds.map(id => getById(id)))
      res = singersDTO.flatten
    } yield res
  }
}
