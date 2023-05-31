package service

import dto.GroupDTO
import repository.{GroupRepository, GroupsAlbumsRepository, GroupsSingersRepository, GroupsSongsRepository}
import models.MusicianPerformer

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class GroupService(){
  def getById(id: Long) = GroupService.getById(id)
  def getAllById(ids: Seq[Long]) = GroupService.getAllById(ids)
  def getAll = GroupService.getAll()
}
object GroupService {

  def getById(id: Long) = {
    for {
      group <- GroupRepository.getById(id)
      groupsSongsIds <- GroupsSongsRepository.getAllByGroupId(id)
      groupsSongs <- SongService.getAllById(groupsSongsIds.toList.map(_.songId))
      groupsAlbumsIds <- GroupsAlbumsRepository.getAllByGroupId(id)
      groupsAlbums <- AlbumService.getAllById(groupsAlbumsIds.toList.map(_.albumId))
      groupsSingersIds <- GroupsSingersRepository.getAllByGroupId(id)
      groupsSingers <- SingerService.getAllById(groupsSingersIds.toList.map(_.singerId))
      res = group match {
        case Some(value) => {
          Option(GroupDTO(id = value.id, name = value.name, photo = value.photo, singers = groupsSingers, numOfPlays = value.numOfPlays, songs = groupsSongs, albums = groupsAlbums))
        }
        case None => None
      }
    } yield res
  }

  def getAllById(ids: Seq[Long]) = {
    for {
      groups <- GroupRepository.getAllById(ids.toList)
      groupIds = groups.map(group => group.id)
      groupsDTO <- Future.sequence(groupIds.map(id => getById(id)))
      res = groupsDTO.flatten
    } yield res
  }

  def getAll() = {
    for {
      groups <- GroupRepository.getAll()
      groupsIds = groups.map(group => group.id)
      groupsDTO <- Future.sequence(groupsIds.map(id => getById(id)))
      res = groupsDTO.flatten
    } yield res
  }

  def add(name: String, photo: Array[Byte], numOfPlays: Long) = {
    for {
      temp1 <- GroupRepository.add(MusicianPerformer(id = 0, name = name, photo = photo, numOfPlays = numOfPlays))
      finded <- GroupRepository.findByGroup(numOfPlays, name)
      id = finded match {
        case Some(value) => value.id
        case None => 0L
      }
      res <- GroupService.getById(id)
    } yield res
  }

  def update(id: Long, name: String, photo: Array[Byte], numOfPlays: Long) = {
    for {
      temp <- GroupRepository.update(id, MusicianPerformer(id = id, name = name, photo = photo, numOfPlays = numOfPlays))
      res <- GroupService.getById(id)
    } yield res
  }

  def delete(id: Long) = {
    for {
      answer <- GroupRepository.delete(id)
      temp1 <- GroupsSongsRepository.deleteAllByGroupId(id)
      temp2 <- GroupsAlbumsRepository.deleteAllByGroupId(id)
      temp3 <- GroupsSingersRepository.deleteAllByGroupId(id)
      res = if (answer == 0) None else Option(id)
    } yield res
  }
}
