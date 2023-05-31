package graphql

import com.google.inject.Inject
import dto.GroupDTO
import sangria.schema._
import service.GroupService

case class MainContext @Inject()(singerContext: SingerContext, songContext: SongContext, albumContext: AlbumContext, groupContext: GroupContext)
case class MainSchema @Inject()(songSchema: SongSchema, albumSchema: AlbumSchema, singerSchema: SingerSchema, groupSchema: GroupSchema){
  var mainQuery = songSchema.songQueryType.concat(albumSchema.albumQueryType).concat(singerSchema.singerQueryType).concat(groupSchema.groupQueryType)
  var mainMutation = songSchema.songMutationType.concat(albumSchema.albumMutationType).concat(singerSchema.singerMutationType).concat(groupSchema.groupMutationType)
  val schema = sangria.schema.Schema(
    query = ObjectType(
      name = "Query",
      fields = fields(mainQuery: _*)),
    mutation = Some(
      ObjectType("Mutation",
      fields = fields(mainMutation: _*)),
    ))
}


