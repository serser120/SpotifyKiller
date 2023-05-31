package graphql

import dto.GroupDTO
import sangria.execution.deferred.HasId
import sangria.schema._
import service._
import dto.GroupDTO.ByteArray

case class GroupSchema() {
  implicit val songHasId = HasId[GroupDTO, Long](_.id)

  val groupQueryType: Seq[Field[Unit, Unit]] = Seq(
    Field(
      name = "getAllGroups",
      fieldType = ListType(GroupDTO.groupGraphQL),
      resolve = _ => GroupService.getAll
    ),
    //      Field("getAllGroupsById", ListType(GroupDTO.groupGraphQL), arguments = List(Argument("id", LongType)), resolve = context => GroupService.getAllById(context.args.arg[Seq[Long]]("id"))),
    Field(
      name = "getGroupById",
      fieldType = OptionType(GroupDTO.groupGraphQL),
      arguments = List(Argument("id", LongType)),
      resolve = context => GroupService.getById(context.args.arg[Long]("id"))
    ),
    Field(
      name = "getGroupByName",
      fieldType = ListType(GroupDTO.groupGraphQL),
      arguments = Argument("name", StringType) :: Nil,
      resolve = context => GroupService.getByName(context.args.arg[String]("name"))
    )
  )

  val groupIdArg = Argument("groupId", LongType)
  val nameArg = Argument("name", StringType)
  val photoArg = Argument("photo", ByteArray)
  val numOfPlaysArg = Argument("numOfPlays", LongType)

  val groupMutationType: List[Field[Unit, Unit]] = List(
    Field(
      name = "addGroup",
      fieldType = OptionType(GroupDTO.groupGraphQL),
      arguments = nameArg :: photoArg :: numOfPlaysArg :: Nil,
      resolve = context => GroupService.add(
        context.args.arg[String]("name"),
        context.args.arg[Array[Byte]]("photo"),
        context.args.arg[Long]("numOfPlays")
      )
    ),
    Field(
      name = "updateGroup",
      fieldType = OptionType(GroupDTO.groupGraphQL),
      arguments = groupIdArg :: nameArg :: photoArg :: numOfPlaysArg :: Nil,
      resolve = context => GroupService.update(
        context.args.arg[Long]("groupId"),
        context.args.arg[String]("name"),
        context.args.arg[Array[Byte]]("photo"),
        context.args.arg[Long]("numOfPlays")
      )
    ),
    Field(
      name = "deleteGroup",
      fieldType = OptionType(LongType),
      arguments = groupIdArg :: Nil,
      resolve = context => GroupService.delete(
        context.args.arg[Long]("groupId")
      )
    )
  )
}

case class GroupContext(context: GroupService)

