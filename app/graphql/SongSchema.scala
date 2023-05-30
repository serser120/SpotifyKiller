package graphql

import com.google.inject.Inject
import dto.{AlbumDTO, SingerDTO, SongDTO}
import sangria.execution.deferred.{Fetcher, HasId}
import sangria.macros.derive.{ReplaceInputField, deriveInputObjectType, deriveObjectType}
import sangria.marshalling.FromInput
import sangria.schema._
import sangria.util.tag.@@
import service.SongService

case class SongSchema @Inject()(songService: SongService) {

  implicit lazy val songHasId = HasId[SongDTO, Long](_.id)

  val songQueryType: Seq[Field[Unit, Unit]] = Seq(
    Field("getAllSongs", ListType(SongDTO.songGraphQL), resolve = _ => SongService.getAll),
    Field("getSongById", OptionType(SongDTO.songGraphQL), arguments = List(Argument("id", LongType)), resolve = context => SongService.getById(context.args.arg[Long]("id"))),
//    Field("getAllSongsById", ListType(SongDTO.songGraphQL), arguments = List(Argument("id", LongType)), resolve = context => SongService.getAllById(context.args.arg[Seq[Long]]("id")))
  )
}

case class SongContext(context: SongService)