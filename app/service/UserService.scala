package service

import dto.{SongDTO, UserDTO}
import dto.histories.HistorySongDTO
import models.Genres.{Genre, Jazz}
import models.user.Roles.user
import models.user._
import repository.user._
import repository.{GroupsSongsRepository, SingersSongsRepository, SongRepository}

import java.time.LocalDate
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, blocking}

class UserService {

}

object UserService {

  def getByToken(token: Long) = {
    for {
      user <- UserRepository.getByToken(token)
      userId: Long = user.map(_.id).getOrElse(0L)
      usersSongsIds <- LikedSongsRepository.getLikedSongs(userId)
      songsIds = usersSongsIds.map(_.songId)
      songsDTO <- SongService.getAllById(songsIds.toList)
      usersAlbumsIds <- LikedAlbumRepository.getLikedAlbums(userId)
      albumsIds = usersAlbumsIds.map(_.albumId)
      albumsDTO <- AlbumService.getAllById(albumsIds)
      usersSingersIds <- LikedSingersRepository.getLikedSingers(userId)
      singersIds = usersSingersIds.map(_.singerId)
      singersDTO <- SingerService.getAllById(singersIds)
      usersGroupsIds <- LikedGroupRepository.getLikedGroups(userId)
      groupsIds = usersGroupsIds.map(_.groupId)
      groupsDTO <- GroupService.getAllById(groupsIds)
      historySongsDTO <- HistoryService.getHistorySongsByUserId(userId)
      historyGenresDTO <- HistoryService.getHistoryGenresByUserId(userId)
      historySingersDTO <- HistoryService.getHistorySingersByUserId(userId)
      historyGroupsDTO <- HistoryService.getHistoryGroupByUserId(userId)
      res = user match {
        case Some(value) => {
          Option(UserDTO(id = value.id, login = value.login, password = value.password, email = value.email, role = value.role, token = value.token,
            likedSongs = songsDTO, likedAlbums = albumsDTO, likedSingers = singersDTO, likedGroups = groupsDTO, historySong = historySongsDTO, historyGenre = historyGenresDTO,
            historySinger = historySingersDTO, historyGroup = historyGroupsDTO))
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
      likedSongsDTO <- SongService.getAllById(songsIds.toList)
      genres = likedSongsDTO.map(_.genre)
      allSongs <- SongService.getAll()
      songsWithGoodGenres = allSongs.map(song => {
        if (genres.contains(song.genre)) song else null
      })
      songsWithGoodGenresIds = songsWithGoodGenres.map(_.id)
      recommendedSongsIds = recommendedSongsByGroupsIds.concat(recommendedSongsBySingersIds).concat(songsWithGoodGenresIds)
      recommendedSongs <- SongService.getAllById(recommendedSongsIds.toList)
      res = randomiseRecommended(recommendedSongs)
    } yield res
  }

  def randomiseRecommended(recommendedSongs: Seq[SongDTO]): Seq[SongDTO] = {
    recommendedSongs.sortWith(_.length > _.length + Math.random() * 180 - Math.random() * 180)
  }

  def playSong(token: Long, songId: Long) = {
    for {
      tokenFlag <- IdsValidator.userTokenValidate(token)
      songFlag <- IdsValidator.songIdValidate(songId)
      user <- UserRepository.getByToken(token)
      userId: Long = user.map(_.id).getOrElse(0L)
      song <- SongRepository.getById(songId)
      songGenre: Genre = song.map(_.genre).getOrElse(Jazz)
      singersSongs <- SingersSongsRepository.getBySongId(songId)
      singer = singersSongs.headOption
      singerId = singer.map(_.singerId).getOrElse(0L)
      groupsSongs <- GroupsSongsRepository.getBySongId(songId)
      group = groupsSongs.headOption
      groupId = group.map(_.groupId).getOrElse(0L)
      res = if (tokenFlag && songFlag) {
        HistorySongRepository.addHistory(userId, songId)
        HistoryGenreRepository.addHistory(userId, songGenre)
        HistorySingerRepository.addHistory(userId, singerId)
        HistoryGroupRepository.addHistory(userId, groupId)
        "*Music play now*"
      } else {
        "Cant play"
      }
    } yield res
  }

  def getSongsActivity(token: Long) = {
    for {
      user <- UserRepository.getByToken(token)
      userId: Long = user.map(_.id).getOrElse(0L)
      historySongs <- HistorySongRepository.getAllByUserId(userId)
      lastYearHistorySongs = historySongs.map(historySong => {
        if (historySong.playingDate.getYear.equals(LocalDate.now().getYear)) historySong
        else null
      })
      songIds: List[Long] = historySongs.map(_.songId).toList
      bestSongsIds = countHistories(songIds)
      songs = bestSongsIds.map(id => for {
        song <- SongService.getById(id)
      } yield song)
      bestSongsOption <- Future.sequence(songs)
      bestSongs = bestSongsOption.flatten
      res = bestSongs
    } yield res
  }

  def countHistories(songIds: List[Long]) = {

    var resMap: Map[Long, Int] = Map[Long, Int]()
    def addResMap(newMember: (Long, Int)) = resMap + newMember
    def updateResMap(id: Long, newNum: Int) = resMap.updated(id, newNum)
    def iterateResMap = for (i <- songIds.indices) {
      resMap = if (resMap.getOrElse(songIds(i), 0L) != 0L) {
        val oldNum = resMap.apply(songIds(i))
        val newNum = oldNum + 1
        updateResMap(songIds(i), newNum)
      } else {
        val newMember: (Long, Int) = (songIds(i), 1)
        addResMap(newMember)
      }
    }
    iterateResMap
    def turnOverMap(map: Map[Long, Int]) = map.toSeq.sortBy(-_._2).map(keyValue => keyValue._1)
    val sortedMap = turnOverMap(resMap)
    val bestSeq = sortedMap.take(5)
    bestSeq
  }

}
