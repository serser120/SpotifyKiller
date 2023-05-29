package graphql

import dto.AlbumDTO
import sangria.execution.deferred.{Fetcher, HasId}
import sangria.marshalling.FromInput
import sangria.schema._
import sangria.util.tag.@@
import service.AlbumService

case class AlbumSchema() {
  implicit val songHasId = HasId[AlbumDTO, Long](_.id)
  val id = Argument("id", LongType)
  val ids: Argument[Seq[Long @@ FromInput.CoercedScalaResult]] = Argument("ids", ListInputType(LongType))

  val albumQueryType = ObjectType(
    name = "albumQuery",
    fields = fields[AlbumService, Unit](
      Field("getAllAlbums", ListType(AlbumDTO.albumGraphQL), resolve = context => context.ctx.getAll),
      Field("getAllAlbumsById", ListType(AlbumDTO.albumGraphQL), arguments = ids :: Nil, resolve = context => context.ctx.getAllById(context arg ids)),
      Field("getAlbumById", OptionType(AlbumDTO.albumGraphQL), arguments = id :: Nil, resolve = context => context.ctx.getById(context arg id))
    )
  )
  val albumSchema: Schema[AlbumService, Unit] = Schema(albumQueryType)

}

case class AlbumContext(context: AlbumService)

