package graphql

import dto.SingerDTO
import sangria.execution.deferred.{Fetcher, HasId}
import sangria.marshalling.FromInput
import sangria.schema._
import sangria.util.tag.@@
import service.SingerService

object SingerSchema {
  implicit val songHasId = HasId[SingerDTO, Long](_.id)
  val id = Argument("id", LongType)
  val ids: Argument[Seq[Long @@ FromInput.CoercedScalaResult]] = Argument("ids", ListInputType(LongType))

  val singerQueryType = ObjectType(
    name = "singerQuery",
    fields = fields[SingerService, Unit](
//      Field("getAllSingers", ListType(SingerDTO.singerGraphQL), resolve = context => context.ctx.getAll),
//      Field("getAllSingersById", ListType(SingerDTO.singerGraphQL), arguments = ids :: Nil, resolve = context => singerFetcher.deferSeq(context arg ids)),
//      Field("getSingerById", OptionType(SingerDTO.singerGraphQL), arguments = id :: Nil, resolve = context => singerFetcher.deferOpt(context arg id))
    )
  )
  val singerSchema: Schema[SingerService, Unit] = Schema(singerQueryType)

}

case class SingerContext(context: SingerService)

