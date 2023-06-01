package service

import com.github.tminglei.slickpg.utils.PgTokenHelper.Token
import repository._
import repository.user.{LikedAlbumRepository, LikedGroupRepository, LikedSingersRepository, LikedSongsRepository, UserRepository}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

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

  def userTokenValidate(token: Long) =
    for {
      model <- UserRepository.getByToken(token)
      res = model match {
        case Some(value) => true
        case None => false
      }
    } yield res

  def likedSongValidate(userId: Long, songId: Long): Future[Boolean] =
    for {
      model <- LikedSongsRepository.getLikedSongById(userId, songId)
      res = model match {
        case Some(value) => true
        case None => false
      }
    } yield res

  def likedAlbumValidate(userId: Long, albumId: Long): Future[Boolean] =
    for {
      model <- LikedAlbumRepository.getLikedAlbumById(userId, albumId)
      res = model match {
        case Some(value) => true
        case None => false
      }
    } yield res

  def likedSingersValidate(userId: Long, singerId: Long): Future[Boolean] =
    for {
      model <- LikedSingersRepository.getLikedSingerById(userId, singerId)
      res = model match {
        case Some(value) => true
        case None => false
      }
    } yield res

  def likedGroupsValidate(userId: Long, groupId: Long): Future[Boolean] =
    for {
      model <- LikedGroupRepository.getLikedGroupById(userId, groupId)
      res = model match {
        case Some(value) => true
        case None => false
      }
    } yield res

}





