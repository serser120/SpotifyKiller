package graphql

import com.google.inject.Inject
import dto.GroupDTO.ByteArray
import dto.SongDTO
import models.Genres.{Genre, GenreType}
import sangria.execution.deferred.HasId
import sangria.schema._
import service.SongService

case class SongSchema @Inject()(songService: SongService) {

  implicit lazy val songHasId = HasId[SongDTO, Long](_.id)

  val songQueryType: Seq[Field[Unit, Unit]] = Seq(
    Field(name = "getAllSongs",
      fieldType = ListType(SongDTO.songGraphQL),
      resolve = _ => SongService.getAll
    ),
    Field(name = "getSongById",
      fieldType = OptionType(SongDTO.songGraphQL),
      arguments = List(Argument("id", LongType)),
      resolve = context => SongService.getById(context.args.arg[Long]("id"))
    ),
    Field(name = "getSongByName",
      fieldType = ListType(SongDTO.songGraphQL),
      arguments = Argument("name", StringType) :: Nil,
      resolve = context => SongService.getByName(context.args.arg[String]("name"))
    )
    //    Field("getAllSongsById", ListType(SongDTO.songGraphQL), arguments = List(Argument("id", LongType)), resolve = context => SongService.getAllById(context.args.arg[Seq[Long]]("id")))
  )

  val songIdArg1 = Argument("songId", LongType)
  val albumIdArg = Argument("albumId", LongType)
  val singerIdArg = Argument("singerId", LongType)
  val groupIdArg = Argument("groupId", LongType)
  val nameArg = Argument("name", StringType)
  val photoArg = Argument("photo", ByteArray)
  val lengthArg = Argument("length", IntType)
  val songArg = Argument("song", ByteArray)
  val genreArg = Argument("genre", GenreType)

  val songMutationType: List[Field[Unit, Unit]] = List(
    Field(
      name = "addSong",
      fieldType = OptionType(SongDTO.songGraphQL),
      arguments = nameArg :: photoArg :: lengthArg :: songArg :: genreArg :: Nil,
      resolve = context => SongService.add(
        context.args.arg[String]("name"),
        context.args.arg[Array[Byte]]("photo"),
        context.args.arg[Int]("length"),
        context.args.arg[Array[Byte]]("song"),
        context.args.arg[Genre]("genre")
      )
    ),
    Field(
      name = "updateSong",
      fieldType = OptionType(StringType),
      arguments = songIdArg1 :: nameArg :: photoArg :: lengthArg :: songArg :: genreArg :: Nil,
      resolve = context => SongService.update(
        context.args.arg[Long]("songId"),
        context.args.arg[String]("name"),
        context.args.arg[Array[Byte]]("photo"),
        context.args.arg[Int]("length"),
        context.args.arg[Array[Byte]]("song"),
        context.args.arg[Genre]("genre")
      )
    ),
    Field(
      name = "deleteSong",
      fieldType = OptionType(StringType),
      arguments = songIdArg1 :: Nil,
      resolve = context => SongService.delete(
        context.args.arg[Long]("songId")
      )
    ),
    Field(
      name = "addSongToAlbum",
      fieldType = OptionType(StringType),
      arguments = songIdArg1 :: albumIdArg :: Nil,
      resolve = context => SongService.addSongToAlbum(
        context.args.arg[Long]("songId"),
        context.args.arg[Long]("albumId")
      )
    ),
    Field(
      name = "deleteSongFromAlbum",
      fieldType = OptionType(StringType),
      arguments = songIdArg1 :: albumIdArg :: Nil,
      resolve = context => SongService.deleteSongFromAlbum(
        context.args.arg[Long]("songId"),
        context.args.arg[Long]("albumId")
      )
    ),
    Field(
      name = "addSongToSinger",
      fieldType = OptionType(StringType),
      arguments = songIdArg1 :: singerIdArg :: Nil,
      resolve = context => SongService.addSongToSinger(
        context.args.arg[Long]("songId"),
        context.args.arg[Long]("singerId")
      )
    ),
    Field(
      name = "deleteSongFromSinger",
      fieldType = OptionType(StringType),
      arguments = songIdArg1 :: singerIdArg :: Nil,
      resolve = context => SongService.deleteSongFromSinger(
        context.args.arg[Long]("songId"),
        context.args.arg[Long]("singerId")
      )
    ),
    Field(
      name = "addSongToGroup",
      fieldType = OptionType(StringType),
      arguments = songIdArg1 :: groupIdArg :: Nil,
      resolve = context => SongService.addSongToGroup(
        context.args.arg[Long]("songId"),
        context.args.arg[Long]("groupId")
      )
    ),
    Field(
      name = "deleteSongFromGroup",
      fieldType = OptionType(StringType),
      arguments = songIdArg1 :: groupIdArg :: Nil,
      resolve = context => SongService.deleteSongFromGroup(
        context.args.arg[Long]("songId"),
        context.args.arg[Long]("groupId")
      )
    )
  )
}

case class SongContext(context: SongService)