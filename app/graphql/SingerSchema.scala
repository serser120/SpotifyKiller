package graphql

import dto.SingerDTO
import sangria.execution.deferred.{Fetcher, HasId}
import sangria.marshalling.FromInput
import sangria.schema._
import sangria.util.tag.@@
import service.SingerService

case class SingerSchema() {
  implicit val singerHasId = HasId[SingerDTO, Long](_.id)

  val singerQueryType: Seq[Field[Unit, Unit]] = Seq(
      Field("getAllSingers", ListType(SingerDTO.singerGraphQL), resolve = _ => SingerService.getAll),
//      Field("getAllSingersById", ListType(SingerDTO.singerGraphQL), arguments = List(Argument("id", LongType)), resolve = context => SingerService.getAllById(context.args.arg[Seq[Long]]("id"))),
      Field("getSingerById", OptionType(SingerDTO.singerGraphQL), arguments = List(Argument("id", LongType)), resolve = context => SingerService.getById(context.args.arg[Long]("id")))
  )
}

case class SingerContext(context: SingerService)

