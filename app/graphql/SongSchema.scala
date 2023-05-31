package graphql

import com.google.inject.Inject
import dto.GroupDTO.ByteArray
import dto.SongDTO
import sangria.execution.deferred.HasId
import sangria.schema._
import service.SongService

case class SongSchema @Inject()(songService: SongService) {

  implicit lazy val songHasId = HasId[SongDTO, Long](_.id)

  val songQueryType: Seq[Field[Unit, Unit]] = Seq(
    Field(name = "getAllSongs",
      fieldType = ListType(SongDTO.songGraphQL),
      resolve = _ => SongService.getAll),
    Field(name = "getSongById",
      fieldType = OptionType(SongDTO.songGraphQL),
      arguments = List(Argument("id", LongType)),
      resolve = context => SongService.getById(context.args.arg[Long]("id"))),
    //    Field("getAllSongsById", ListType(SongDTO.songGraphQL), arguments = List(Argument("id", LongType)), resolve = context => SongService.getAllById(context.args.arg[Seq[Long]]("id")))
  )

  val songIdArg1 = Argument("songId", LongType)
  val albumIdArg = Argument("albumId", LongType)
  val nameArg = Argument("name", StringType)
  val photoArg = Argument("photo", ByteArray)
  val lengthArg = Argument("length", IntType)
  val songArg = Argument("song", ByteArray)

  val songMutationType: List[Field[Unit, Unit]] = List(
    Field(
      name = "addSong",
      fieldType = OptionType(SongDTO.songGraphQL),
      arguments = nameArg :: photoArg :: lengthArg :: songArg :: Nil,
      resolve = context => SongService.add(
        context.args.arg[String]("name"),
        context.args.arg[Array[Byte]]("photo"),
        context.args.arg[Int]("length"),
        context.args.arg[Array[Byte]]("song")
      )
    ),
    Field(
      name = "updateSong",
      fieldType = OptionType(StringType),
      arguments = songIdArg1 :: nameArg :: photoArg :: lengthArg :: songArg :: Nil,
      resolve = context => SongService.update(
        context.args.arg[Long]("songId"),
        context.args.arg[String]("name"),
        context.args.arg[Array[Byte]]("photo"),
        context.args.arg[Int]("length"),
        context.args.arg[Array[Byte]]("song")
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
    )
  )
}

case class SongContext(context: SongService)