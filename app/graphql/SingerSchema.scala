package graphql

import dto.GroupDTO.ByteArray
import dto.SingerDTO
import sangria.execution.deferred.HasId
import sangria.schema._
import service.SingerService

case class SingerSchema() {
  implicit val singerHasId = HasId[SingerDTO, Long](_.id)

  val singerQueryType: Seq[Field[Unit, Unit]] = Seq(
    Field(
      name = "getAllSingers",
      fieldType = ListType(SingerDTO.singerGraphQL),
      resolve = _ => SingerService.getAll
    ),
    //      Field("getAllSingersById", ListType(SingerDTO.singerGraphQL), arguments = List(Argument("id", LongType)), resolve = context => SingerService.getAllById(context.args.arg[Seq[Long]]("id"))),
    Field(
      name = "getSingerById",
      fieldType = OptionType(SingerDTO.singerGraphQL),
      arguments = List(Argument("id", LongType)),
      resolve = context => SingerService.getById(context.args.arg[Long]("id"))
    ),
    Field(
      name = "getSingerByName",
      fieldType = ListType(SingerDTO.singerGraphQL),
      arguments = Argument("name", StringType) :: Nil,
      resolve = context => SingerService.getByName(context.args.arg[String]("name"))
    )
  )

  val singerIdArg = Argument("singerId", LongType)
  val groupIdArg = Argument("groupId", LongType)
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
      arguments = singerIdArg :: nameArg :: photoArg :: numOfPlaysArg :: Nil,
      resolve = context => SingerService.update(
        context.args.arg[Long]("singerId"),
        context.args.arg[String]("name"),
        context.args.arg[Array[Byte]]("photo"),
        context.args.arg[Long]("numOfPlays")
      )
    ),
    Field(
      name = "deleteSinger",
      fieldType = OptionType(LongType),
      arguments = singerIdArg :: Nil,
      resolve = context => SingerService.delete(
        context.args.arg[Long]("singerId")
      )
    ),
    Field(
      name = "addSingerToGroup",
      fieldType = OptionType(StringType),
      arguments = singerIdArg :: groupIdArg :: Nil,
      resolve = context => SingerService.addSingerToGroup(
        context.args.arg[Long]("singerId"),
        context.args.arg[Long]("groupId")
      )
    ),
    Field(
      name = "deleteSingerFromGroup",
      fieldType = OptionType(StringType),
      arguments = singerIdArg :: groupIdArg :: Nil,
      resolve = context => SingerService.deleteSingerFromGroup(
        context.args.arg[Long]("singerId"),
        context.args.arg[Long]("groupId")
      )
    )
  )
}

case class SingerContext(context: SingerService)

