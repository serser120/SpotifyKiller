package service

import dto.GroupDTO
import repository.{GroupRepository, GroupsAlbumsRepository, GroupsSingersRepository, GroupsSongsRepository}

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
      res <- Future.sequence(groupIds.map(id => getById(id)))
    } yield res
  }

  def getAll() = {
    for {
      groups <- GroupRepository.getAll()
      groupsIds = groups.map(group => group.id)
      res <- Future.sequence(groupsIds.map(id => getById(id)))
    } yield res
  }
}
