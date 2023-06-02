package service

import dto.SongDTO
import dto.histories.{HistoryGenreDTO, HistoryGroupDTO, HistorySingerDTO, HistorySongDTO}
import repository.user.{HistoryGenreRepository, HistoryGroupRepository, HistorySingerRepository, HistorySongRepository}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object HistoryService {

  def getHistorySongsByUserId(userId: Long) = {
    for {
      historySongsModel <- HistorySongRepository.getAllByUserId(userId)
      historySongsDTO = historySongsModel.map(historySong => for {
        songOption <- SongService.getById(historySong.songId)
        song = songOption match {
          case Some(value) => value
          case None => null
        }
        historySongDTO = HistorySongDTO(userId = historySong.userId, songDTO = song, playingDate = historySong.playingDate.toString)
      } yield historySongDTO)
      res <- Future.sequence(historySongsDTO)
    } yield res
  }

  def getHistoryGenresByUserId(userId: Long) = {
    for {
      historyGenresModel <- HistoryGenreRepository.getAllByUserId(userId)
      res = historyGenresModel.map(q => HistoryGenreDTO(userId = q.userId, genre = q.genre, playingDate = q.playingDate.toString))
    } yield res
  }

  def getHistorySingersByUserId(userId: Long) = {
    for {
      historySingersModel <- HistorySingerRepository.getAllByUserId(userId)
      historySingersDTO = historySingersModel.map(historySinger => for {
        singerOption <- SingerService.getById(historySinger.singerId)
        singer = singerOption match {
          case Some(value) => value
          case None => null
        }
        historySingerDTO = HistorySingerDTO(userId = historySinger.userId, singerDTO = singer, playingDate = historySinger.playingDate.toString)
      } yield historySingerDTO)
      res <- Future.sequence(historySingersDTO)
    } yield res
  }

  def getHistoryGroupByUserId(userId: Long) = {
    for {
      historyGroupsModel <- HistoryGroupRepository.getAllByUserId(userId)
      historyGroupsDTO = historyGroupsModel.map(historyGroup => for {
        groupOption <- GroupService.getById(historyGroup.groupId)
        group = groupOption match {
          case Some(value) => value
          case None => null
        }
        historyGroupDTO = HistoryGroupDTO(userId = historyGroup.userId, groupDTO = group, playingDate = historyGroup.playingDate.toString)
      } yield historyGroupDTO)
      res <- Future.sequence(historyGroupsDTO)
    } yield res
  }

}
