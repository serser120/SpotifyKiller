package graphql

import dto.GroupDTO
import sangria.execution.deferred.{Fetcher, HasId}
import sangria.marshalling.FromInput
import sangria.schema._
import sangria.util.tag.@@
import service._

object GroupSchema {
  implicit val songHasId = HasId[GroupDTO, Long](_.id)
  val id = Argument("id", LongType)
  val ids: Argument[Seq[Long @@ FromInput.CoercedScalaResult]] = Argument("ids", ListInputType(LongType))

  val groupQueryType = ObjectType(
    name = "groupQuery",
    fields = fields[GroupService, Unit](
//      Field("getAllGroups", ListType(GroupDTO.groupGraphQL), resolve = context => context.ctx.getAll),
//      Field("getAllGroupsById", ListType(GroupDTO.groupGraphQL), arguments = ids :: Nil, resolve = context => context.ctx.getAllById(context arg ids)),
      Field("getGroupById", OptionType(GroupDTO.groupGraphQL), arguments = id :: Nil, resolve = context => context.ctx.getById(context arg id))
    )
  )
  val groupSchema: Schema[GroupService, Unit] = Schema(groupQueryType)

}

case class GroupContext(context: GroupService)

