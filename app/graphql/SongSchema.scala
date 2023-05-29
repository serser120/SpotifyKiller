package graphql

import dto.{AlbumDTO, SingerDTO, SongDTO}
import sangria.execution.deferred.{Fetcher, HasId}
import sangria.macros.derive.{ReplaceInputField, deriveInputObjectType, deriveObjectType}
import sangria.marshalling.FromInput
import sangria.schema._
import sangria.util.tag.@@
import service.SongService

case class SongSchema() {

  implicit lazy val songHasId = HasId[SongDTO, Long](_.id)

  val id = Argument("id", LongType)
  val ids: Argument[Seq[Long @@ FromInput.CoercedScalaResult]] = Argument("ids", ListInputType(LongType))

  val songQueryType = ObjectType[SongService, Unit](
    name = "songQuery",
    fields = fields[SongService, Unit](
      Field("getAllSongs", ListType(SongDTO.songGraphQL), resolve = context => context.ctx.getAll),
      Field("getAllSongsById", ListType(SongDTO.songGraphQL), arguments = ids :: Nil, resolve = context => context.ctx.getAllById(context arg ids)),
      Field("getSongById", OptionType(SongDTO.songGraphQL), arguments = id :: Nil, resolve = context => context.ctx.getById(context arg id))
      )
    )
  val songSchema: Schema[SongService, Unit] = Schema(songQueryType)

}

case class SongContext(context: SongService)