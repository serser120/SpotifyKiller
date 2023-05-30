package graphql

import dto.AlbumDTO
import sangria.execution.deferred.HasId
import sangria.schema._
import service.AlbumService

case class AlbumSchema() {
  implicit val albumHasId = HasId[AlbumDTO, Long](_.id)

  val albumQueryType: Seq[Field[Unit, Unit]] = Seq(
      Field("getAllAlbums", ListType(AlbumDTO.albumGraphQL), resolve = _ => AlbumService.getAll),
//      Field("getAllAlbumsById", ListType(AlbumDTO.albumGraphQL), arguments = List(Argument("id", LongType)), resolve = context => AlbumService.getAllById(context.args.arg[Seq[Long]]("id"))),
      Field("getAlbumById", OptionType(AlbumDTO.albumGraphQL), arguments = List(Argument("id", LongType)), resolve = context => AlbumService.getById(context.args.arg[Long]("id")))
  )
}
case class AlbumContext(context: AlbumService)

