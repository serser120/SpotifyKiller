package service

import dto.{SongDTO, UserDTO}
import models.SingersSongs
import models.user.Roles.user
import models.user._
import repository.{GroupsSongsRepository, SingersSongsRepository}
import repository.user.{LikedAlbumRepository, LikedGroupRepository, LikedSingersRepository, LikedSongsRepository, UserRepository}

import java.time.LocalDateTime
import java.util.Calendar
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

class UserService {

}

object UserService {

  def getByToken(token: Long) = {
    for {
      user <- UserRepository.getByToken(token)
      userId: Long = user.map(_.id).getOrElse(0L)
      usersSongsIds <- LikedSongsRepository.getLikedSongs(userId)
      songsIds = usersSongsIds.map(_.songId)
      songsDTO <- SongService.getAllById(songsIds)
      usersAlbumsIds <- LikedAlbumRepository.getLikedAlbums(userId)
      albumsIds = usersAlbumsIds.map(_.albumId)
      albumsDTO <- AlbumService.getAllById(albumsIds)
      usersSingersIds <- LikedSingersRepository.getLikedSingers(userId)
      singersIds = usersSingersIds.map(_.singerId)
      singersDTO <- SingerService.getAllById(singersIds)
      usersGroupsIds <- LikedGroupRepository.getLikedGroups(userId)
      groupsIds = usersGroupsIds.map(_.groupId)
      groupsDTO <- GroupService.getAllById(groupsIds)
      res = user match {
        case Some(value) => {
          Option(UserDTO(id = value.id, login = value.login, password = value.password, email = value.email, role = value.role, token = value.token,
            likedSongs = songsDTO, likedAlbums = albumsDTO, likedSingers = singersDTO, likedGroups = groupsDTO))
        }
        case None => None
      }
    } yield res
  }

  def registerUser(login: String, password: String, email: String) = {
    val token = System.currentTimeMillis()
    for {
      temp <- UserRepository.registerUser(User(id = 1, login = login, password = password, email = email, role = user, token = token))
      res <- UserService.getByToken(token)
    } yield res
  }

  def likeSong(token: Long, songId: Long) = {
    for {
      user <- UserRepository.getByToken(token)
      userId: Long = user.map(_.id).getOrElse(0L)
      tokenFlag <- IdsValidator.userTokenValidate(token)
      songFlag <- IdsValidator.songIdValidate(songId)
      likedSongFlag <- IdsValidator.likedSongValidate(userId, songId)
      res = if (tokenFlag && songFlag && !likedSongFlag) {
        LikedSongsRepository.likeSong(LikedSongs(userId, songId))
        "Success"
      } else {
        "Cant like song"
      }
    } yield res
  }

  def likeAlbum(token: Long, albumId: Long) = {
    for {
      user <- UserRepository.getByToken(token)
      userId: Long = user.map(_.id).getOrElse(0L)
      tokenFlag <- IdsValidator.userTokenValidate(token)
      albumFlag <- IdsValidator.albumIdValidate(albumId)
      likedAlbumFlag <- IdsValidator.likedAlbumValidate(userId, albumId)
      res = if (tokenFlag && albumFlag && !likedAlbumFlag) {
        LikedAlbumRepository.likeAlbum(LikedAlbums(userId, albumId))
        "Success"
      } else {
        "Cant like album"
      }
    } yield res
  }

  def likeSinger(token: Long, singerId: Long) = {
    for {
      user <- UserRepository.getByToken(token)
      userId: Long = user.map(_.id).getOrElse(0L)
      tokenFlag <- IdsValidator.userTokenValidate(token)
      singerFlag <- IdsValidator.singerIdValidate(singerId)
      likedSingerFlag <- IdsValidator.likedSingersValidate(userId, singerId)
      res = if (tokenFlag && singerFlag && !likedSingerFlag) {
        LikedSingersRepository.likeSinger(LikedSingers(userId, singerId))
        "Success"
      } else {
        "Cant like singer"
      }
    } yield res
  }

  def likeGroup(token: Long, groupId: Long) = {
    for {
      user <- UserRepository.getByToken(token)
      userId: Long = user.map(_.id).getOrElse(0L)
      tokenFlag <- IdsValidator.userTokenValidate(token)
      groupFlag <- IdsValidator.groupIdValidate(groupId)
      likedGroupFlag <- IdsValidator.likedGroupsValidate(userId, groupId)
      res = if (tokenFlag && groupFlag && !likedGroupFlag) {
        LikedGroupRepository.likeGroup(LikedGroups(userId, groupId))
        "Success"
      } else {
        "Cant like singer"
      }
    } yield res
  }

  def deleteLikeSong(token: Long, songId: Long) = {
    for {
      user <- UserRepository.getByToken(token)
      userId: Long = user.map(_.id).getOrElse(0L)
      tokenFlag <- IdsValidator.userTokenValidate(token)
      songFlag <- IdsValidator.songIdValidate(songId)
      likedSongFlag <- IdsValidator.likedSongValidate(userId, songId)
      res = if (tokenFlag && songFlag && likedSongFlag) {
        LikedSongsRepository.deleteLikeSong(userId, songId)
        "Success"
      } else {
        "Cant delete like song"
      }
    } yield res
  }

  def deleteLikeAlbum(token: Long, albumId: Long) = {
    for {
      user <- UserRepository.getByToken(token)
      userId: Long = user.map(_.id).getOrElse(0L)
      tokenFlag <- IdsValidator.userTokenValidate(token)
      albumFlag <- IdsValidator.albumIdValidate(albumId)
      likedAlbumFlag <- IdsValidator.likedAlbumValidate(userId, albumId)
      res = if (tokenFlag && albumFlag && likedAlbumFlag) {
        LikedAlbumRepository.deleteLikeAlbum(userId, albumId)
        "Success"
      } else {
        "Cant delete like album"
      }
    } yield res
  }

  def deleteLikeSinger(token: Long, singerId: Long) = {
    for {
      user <- UserRepository.getByToken(token)
      userId: Long = user.map(_.id).getOrElse(0L)
      tokenFlag <- IdsValidator.userTokenValidate(token)
      singerFlag <- IdsValidator.singerIdValidate(singerId)
      likedSingerFlag <- IdsValidator.likedSingersValidate(userId, singerId)
      res = if (tokenFlag && singerFlag && likedSingerFlag) {
        LikedSingersRepository.deleteLikeSinger(userId, singerId)
        "Success"
      } else {
        "Cant delete like singer"
      }
    } yield res
  }

  def deleteLikeGroup(token: Long, groupId: Long) = {
    for {
      user <- UserRepository.getByToken(token)
      userId: Long = user.map(_.id).getOrElse(0L)
      tokenFlag <- IdsValidator.userTokenValidate(token)
      groupFlag <- IdsValidator.groupIdValidate(groupId)
      likedGroupFlag <- IdsValidator.likedGroupsValidate(userId, groupId)
      res = if (tokenFlag && groupFlag && likedGroupFlag) {
        LikedGroupRepository.deleteLikeGroup(userId, groupId)
        "Success"
      } else {
        "Cant delete like singer"
      }
    } yield res
  }

  def getRecommendedSongs(token: Long) = {
    for {
      user <- UserRepository.getByToken(token)
      userId: Long = user.map(_.id).getOrElse(0L)
      likedSongs <- LikedSongsRepository.getLikedSongs(userId)
      songsIds = likedSongs.map(_.songId)
      //выбор по певцам
      singerSongsFuture = songsIds.map(id => for {
        singersSongs <- SingersSongsRepository.getBySongId(id)
        singersIds = singersSongs.map(_.singerId)
        recommendedSingersSongsFuture = singersIds.map(singerId => for {
          recommendedSingerSongs <- SingersSongsRepository.getAllBySingerId(singerId)
          recommendedSongs = recommendedSingerSongs.map(_.songId)
        } yield recommendedSongs)
        recommendedSingersSongsSeq <- Future.sequence(recommendedSingersSongsFuture)
        res = recommendedSingersSongsSeq.flatMap(q => q)
      } yield res)
      singersSongsSeq <- Future.sequence(singerSongsFuture)
      recommendedSongsBySingersIds = singersSongsSeq.flatMap(q => q)
      //выбор по группам
      groupsSongsFuture = songsIds.map(id => for {
        groupsSongs <- GroupsSongsRepository.getBySongId(id)
        groupsIds = groupsSongs.map(_.groupId)
        recommendedGroupsSongsFuture = groupsIds.map(groupId => for {
          recommendedGroupSongs <- GroupsSongsRepository.getAllByGroupId(groupId)
          recommendedSongs = recommendedGroupSongs.map(_.songId)
        } yield recommendedSongs)
        recommendedGroupsSongsSeq <- Future.sequence(recommendedGroupsSongsFuture)
        res = recommendedGroupsSongsSeq.flatMap(q => q)
      } yield res)
      groupsSongsSeq <- Future.sequence(groupsSongsFuture)
      recommendedSongsByGroupsIds = groupsSongsSeq.flatMap(q => q)
      //выбор по жанрам
      likedSongsDTO <- SongService.getAllById(songsIds)
      genres = likedSongsDTO.map(_.genre)
      allSongs <- SongService.getAll()
      songsWithGoodGenres = allSongs.map(song => {
        if (genres.contains(song.genre)) song else null
      })
      songsWithGoodGenresIds = songsWithGoodGenres.map(_.id)
      recommendedSongsIds = recommendedSongsByGroupsIds.concat(recommendedSongsBySingersIds).concat(songsWithGoodGenresIds)
      recommendedSongs <- SongService.getAllById(recommendedSongsIds)
      res = randomiseRecommended(recommendedSongs)
    } yield res
  }

  def randomiseRecommended(recommendedSongs: Seq[SongDTO]): Seq[SongDTO] = {
    recommendedSongs.sortWith(_.length > _.length + Math.random()*180 - Math.random()*180)
  }

}
