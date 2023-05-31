package graphql

import dto.AlbumDTO
import sangria.execution.deferred.HasId
import sangria.schema._
import service.AlbumService
import dto.GroupDTO.ByteArray

case class AlbumSchema() {
  implicit val albumHasId = HasId[AlbumDTO, Long](_.id)

  val albumQueryType: Seq[Field[Unit, Unit]] = Seq(
    Field(
      name = "getAllAlbums",
      ListType(AlbumDTO.albumGraphQL),
      resolve = _ => AlbumService.getAll
    ),
    //      Field("getAllAlbumsById", ListType(AlbumDTO.albumGraphQL), arguments = List(Argument("id", LongType)), resolve = context => AlbumService.getAllById(context.args.arg[Seq[Long]]("id"))),
    Field(
      name = "getAlbumById",
      OptionType(AlbumDTO.albumGraphQL),
      arguments = List(Argument("id", LongType)),
      resolve = context => AlbumService.getById(context.args.arg[Long]("id"))
    ),
    Field(
      name = "getAlbumByName",
      fieldType = ListType(AlbumDTO.albumGraphQL),
      arguments = Argument("name", StringType) :: Nil,
      resolve = context => AlbumService.getByName(context.args.arg[String]("name"))
    )
  )

  val albumArgId = Argument("albumId", LongType)
  val singerIdArg = Argument("singerId", LongType)
  val groupIdArg = Argument("groupId", LongType)
  val nameArg = Argument("name", StringType)
  val photoArg = Argument("photo", ByteArray)
  val numOfPlaysArg = Argument("numOfPlays", LongType)

  val albumMutationType: List[Field[Unit, Unit]] = List(
    Field(
      name = "addAlbum",
      fieldType = OptionType(AlbumDTO.albumGraphQL),
      arguments = nameArg :: photoArg :: numOfPlaysArg :: Nil,
      resolve = context => AlbumService.add(
        context.args.arg[String]("name"),
        context.args.arg[Array[Byte]]("photo"),
        context.args.arg[Long]("numOfPlays")
      )
    ),
    Field(
      name = "updateAlbum",
      fieldType = OptionType(AlbumDTO.albumGraphQL),
      arguments = albumArgId :: nameArg :: photoArg :: numOfPlaysArg :: Nil,
      resolve = context => AlbumService.update(
        context.args.arg[Long]("albumId"),
        context.args.arg[String]("name"),
        context.args.arg[Array[Byte]]("photo"),
        context.args.arg[Long]("numOfPlays")
      )
    ),
    Field(
      name = "deleteAlbum",
      fieldType = OptionType(LongType),
      arguments = albumArgId :: Nil,
      resolve = context => AlbumService.delete(
        context.args.arg[Long]("albumId")
      )
    ),
    Field(
      name = "addAlbumToSinger",
      fieldType = OptionType(StringType),
      arguments = albumArgId :: singerIdArg :: Nil,
      resolve = context => AlbumService.addAlbumToSinger(
        context.args.arg[Long]("albumId"),
        context.args.arg[Long]("singerId")
      )
    ),
    Field(
      name = "deleteAlbumFromSinger",
      fieldType = OptionType(StringType),
      arguments = albumArgId :: singerIdArg :: Nil,
      resolve = context => AlbumService.deleteAlbumFromSinger(
        context.args.arg[Long]("albumId"),
        context.args.arg[Long]("singerId")
      )
    ),
    Field(
      name = "addAlbumToGroup",
      fieldType = OptionType(StringType),
      arguments = albumArgId :: groupIdArg :: Nil,
      resolve = context => AlbumService.addAlbumToGroup(
        context.args.arg[Long]("albumId"),
        context.args.arg[Long]("groupId")
      )
    ),
    Field(
      name = "deleteAlbumFromGroup",
      fieldType = OptionType(StringType),
      arguments = albumArgId :: groupIdArg :: Nil,
      resolve = context => AlbumService.deleteAlbumFromGroup(
        context.args.arg[Long]("albumId"),
        context.args.arg[Long]("groupId")
      )
    )
  )
}

case class AlbumContext(context: AlbumService)

