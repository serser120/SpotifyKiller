package graphql

import dto.AlbumDTO
import sangria.execution.deferred.HasId
import sangria.schema._
import service.AlbumService
import dto.GroupDTO.ByteArray

case class AlbumSchema() {
  implicit val albumHasId = HasId[AlbumDTO, Long](_.id)

  val albumQueryType: Seq[Field[Unit, Unit]] = Seq(
      Field("getAllAlbums", ListType(AlbumDTO.albumGraphQL), resolve = _ => AlbumService.getAll),
//      Field("getAllAlbumsById", ListType(AlbumDTO.albumGraphQL), arguments = List(Argument("id", LongType)), resolve = context => AlbumService.getAllById(context.args.arg[Seq[Long]]("id"))),
      Field("getAlbumById", OptionType(AlbumDTO.albumGraphQL), arguments = List(Argument("id", LongType)), resolve = context => AlbumService.getById(context.args.arg[Long]("id")))
  )

  val idArg = Argument("id", LongType)
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
      arguments = idArg :: nameArg :: photoArg :: numOfPlaysArg :: Nil,
      resolve = context => AlbumService.update(
        context.args.arg[Long]("id"),
        context.args.arg[String]("name"),
        context.args.arg[Array[Byte]]("photo"),
        context.args.arg[Long]("numOfPlays")
      )
    ),
    Field(
      name = "deleteAlbum",
      fieldType = OptionType(LongType),
      arguments = idArg :: Nil,
      resolve = context => AlbumService.delete(
        context.args.arg[Long]("id")
      )
    )
  )
}
case class AlbumContext(context: AlbumService)

