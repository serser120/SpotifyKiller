package graphql

import dto.GroupDTO.ByteArray
import dto.SingerDTO
import sangria.execution.deferred.HasId
import sangria.schema._
import service.SingerService

case class SingerSchema() {
  implicit val singerHasId = HasId[SingerDTO, Long](_.id)

  val singerQueryType: Seq[Field[Unit, Unit]] = Seq(
    Field("getAllSingers", ListType(SingerDTO.singerGraphQL), resolve = _ => SingerService.getAll),
    //      Field("getAllSingersById", ListType(SingerDTO.singerGraphQL), arguments = List(Argument("id", LongType)), resolve = context => SingerService.getAllById(context.args.arg[Seq[Long]]("id"))),
    Field("getSingerById", OptionType(SingerDTO.singerGraphQL), arguments = List(Argument("id", LongType)), resolve = context => SingerService.getById(context.args.arg[Long]("id")))
  )

  val idArg = Argument("id", LongType)
  val nameArg = Argument("name", StringType)
  val photoArg = Argument("photo", ByteArray)
  val numOfPlaysArg = Argument("numOfPlays", LongType)

  val singerMutationType: List[Field[Unit, Unit]] = List(
    Field(
      name = "addSinger",
      fieldType = OptionType(SingerDTO.singerGraphQL),
      arguments = nameArg :: photoArg :: numOfPlaysArg :: Nil,
      resolve = context => SingerService.add(
        context.args.arg[String]("name"),
        context.args.arg[Array[Byte]]("photo"),
        context.args.arg[Long]("numOfPlays")
      )
    ),
    Field(
      name = "updateSinger",
      fieldType = OptionType(SingerDTO.singerGraphQL),
      arguments = idArg :: nameArg :: photoArg :: numOfPlaysArg :: Nil,
      resolve = context => SingerService.update(
        context.args.arg[Long]("id"),
        context.args.arg[String]("name"),
        context.args.arg[Array[Byte]]("photo"),
        context.args.arg[Long]("numOfPlays")
      )
    ),
    Field(
      name = "deleteSinger",
      fieldType = OptionType(LongType),
      arguments = idArg :: Nil,
      resolve = context => SingerService.delete(
        context.args.arg[Long]("id")
      )
    )
  )
}

case class SingerContext(context: SingerService)

