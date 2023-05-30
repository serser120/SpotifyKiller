package graphql

import dto.GroupDTO
import sangria.execution.deferred.HasId
import sangria.schema._
import service._

case class GroupSchema() {
  implicit val songHasId = HasId[GroupDTO, Long](_.id)

  val groupQueryType: Seq[Field[Unit, Unit]] = Seq(
      Field("getAllGroups", ListType(GroupDTO.groupGraphQL), resolve = _ => GroupService.getAll),
//      Field("getAllGroupsById", ListType(GroupDTO.groupGraphQL), arguments = List(Argument("id", LongType)), resolve = context => GroupService.getAllById(context.args.arg[Seq[Long]]("id"))),
      Field("getGroupById", OptionType(GroupDTO.groupGraphQL), arguments = List(Argument("id", LongType)), resolve = context => GroupService.getById(context.args.arg[Long]("id")))
  )
}

case class GroupContext(context: GroupService)

