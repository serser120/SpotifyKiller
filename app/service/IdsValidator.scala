package service

import repository._

import scala.concurrent.ExecutionContext.Implicits.global

object IdsValidator {
  def songIdValidate(id: Long) =
    for {
      model <- SongRepository.getById(id)
      res = model match {
        case Some(value) => true
        case None => false
      }
    } yield res

  def albumIdValidate(id: Long) =
    for {
      model <- AlbumRepository.getById(id)
      res = model match {
        case Some(value) => true
        case None => false
      }
    } yield res

  def singerIdValidate(id: Long) =
    for {
      model <- SingerRepository.getById(id)
      res = model match {
        case Some(value) => true
        case None => false
      }
    } yield res

  def groupIdValidate(id: Long) =
    for {
      model <- SingerRepository.getById(id)
      res = model match {
        case Some(value) => true
        case None => false
      }
    } yield res

  def albumsSongsIdValidate(albumId: Long, songId: Long) =
    for {
      model <- AlbumsSongsRepository.getById(albumId, songId)
      res = model match {
        case Some(value) => true
        case None => false
      }
    } yield res

  def singersSongsIdValidate(singerId: Long, songId: Long) =
    for {
      model <- SingersSongsRepository.getById(singerId, songId)
      res = model match {
        case Some(value) => true
        case None => false
      }
    } yield res

  def singersAlbumsIdValidate(singerId: Long, albumId: Long) =
    for {
      model <- SingersAlbumsRepository.getById(singerId, albumId)
      res = model match {
        case Some(value) => true
        case None => false
      }
    } yield res

  def groupsSongsIdValidate(groupId: Long, songId: Long) =
    for {
      model <- GroupsSongsRepository.getById(groupId, songId)
      res = model match {
        case Some(value) => true
        case None => false
      }
    } yield res

  def groupsAlbumsIdValidate(groupId: Long, albumId: Long) =
    for {
      model <- GroupsAlbumsRepository.getById(groupId, albumId)
      res = model match {
        case Some(value) => true
        case None => false
      }
    } yield res

  def groupsSingersIdValidate(groupId: Long, singerId: Long) =
    for {
      model <- GroupsSingersRepository.getById(groupId, singerId)
      res = model match {
        case Some(value) => true
        case None => false
      }
    } yield res


}
