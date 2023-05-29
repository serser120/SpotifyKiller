package graphql

import com.google.inject.Inject
import dto.GroupDTO
import sangria.schema._
import service.GroupService

case class MainContext @Inject() (singerContext: SingerContext, songContext: SongContext, albumContext: AlbumContext, groupContext: GroupContext)
case class MainSchema [MainContext, Unit] @Inject() (songSchema: SongSchema, albumSchema: AlbumSchema, singerSchema: SingerSchema, groupSchema: GroupSchema){
  val schema = sangria.schema.Schema(
    query = ObjectType("Query",
      fields(
        songSchema.songQueryType: _*,

      )
    )
}


