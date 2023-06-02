package graphql

import com.google.inject.Inject
import dto.{SongDTO, UserDTO}
import sangria.execution.deferred.HasId
import sangria.schema._
import service._

case class UserSchema @Inject()(userService: UserService)() {
  implicit lazy val userHasId = HasId[UserDTO, Long](_.id)

  val userQueryType: Seq[Field[Unit, Unit]] = Seq(
    Field(
      name = "getByToken",
      fieldType = OptionType(UserDTO.userGraphQL),
      arguments = Argument("token", LongType) :: Nil,
      resolve = context => UserService.getByToken(context.arg[Long]("token"))
    ),
    Field(
      name = "getRecommendedSongs",
      fieldType = ListType(SongDTO.songGraphQL),
      arguments = Argument("token", LongType) :: Nil,
      resolve = context => UserService.getRecommendedSongs(context.arg[Long]("token"))
    ),
    Field(
      name = "getSongsActivity",
      fieldType = ListType(SongDTO.songGraphQL),
      arguments = Argument("token", LongType) :: Nil,
      resolve = context => UserService.getSongsActivity(context.arg[Long]("token"))
    )
  )

  val loginArg = Argument("login", StringType)
  val passwordArg = Argument("password", StringType)
  val emailArg = Argument("email", StringType)
  val tokenArg = Argument("token", LongType)
  val songIdArg = Argument("songId", LongType)
  val albumIdArg = Argument("albumId", LongType)
  val singerIdArg = Argument("singerId", LongType)
  val groupIdArg = Argument("groupId", LongType)

  val userMutationType: List[Field[Unit, Unit]] = List(
    Field(
      name = "registerUser",
      fieldType = OptionType(UserDTO.userGraphQL),
      arguments = loginArg :: passwordArg :: emailArg :: Nil,
      resolve = context => UserService.registerUser(
        context.args.arg[String]("login"),
        context.args.arg[String]("password"),
        context.args.arg[String]("email")
      )
    ),
    Field(
      name = "likeSong",
      fieldType = OptionType(StringType),
      arguments = tokenArg :: songIdArg :: Nil,
      resolve = context => UserService.likeSong(
        context.args.arg[Long]("token"),
        context.args.arg[Long]("songId")
      )
    ),
    Field(
      name = "likeAlbum",
      fieldType = OptionType(StringType),
      arguments = tokenArg :: albumIdArg :: Nil,
      resolve = context => UserService.likeAlbum(
        context.args.arg[Long]("token"),
        context.args.arg[Long]("albumId")
      )
    ),
    Field(
      name = "likeSinger",
      fieldType = OptionType(StringType),
      arguments = tokenArg :: singerIdArg :: Nil,
      resolve = context => UserService.likeSinger(
        context.args.arg[Long]("token"),
        context.args.arg[Long]("singerId")
      )
    ),
    Field(
      name = "likeGroup",
      fieldType = OptionType(StringType),
      arguments = tokenArg :: groupIdArg :: Nil,
      resolve = context => UserService.likeGroup(
        context.args.arg[Long]("token"),
        context.args.arg[Long]("groupId")
      )
    ),
    Field(
      name = "deleteLikeSong",
      fieldType = OptionType(StringType),
      arguments = tokenArg :: songIdArg :: Nil,
      resolve = context => UserService.deleteLikeSong(
        context.args.arg[Long]("token"),
        context.args.arg[Long]("songId")
      )
    ),
    Field(
      name = "deleteLikeAlbum",
      fieldType = OptionType(StringType),
      arguments = tokenArg :: albumIdArg :: Nil,
      resolve = context => UserService.deleteLikeAlbum(
        context.args.arg[Long]("token"),
        context.args.arg[Long]("albumId")
      )
    ),
    Field(
      name = "deleteLikeSinger",
      fieldType = OptionType(StringType),
      arguments = tokenArg :: singerIdArg :: Nil,
      resolve = context => UserService.deleteLikeSinger(
        context.args.arg[Long]("token"),
        context.args.arg[Long]("singerId")
      )
    ),
    Field(
      name = "deleteLikeGroup",
      fieldType = OptionType(StringType),
      arguments = tokenArg :: groupIdArg :: Nil,
      resolve = context => UserService.deleteLikeGroup(
        context.args.arg[Long]("token"),
        context.args.arg[Long]("groupId")
      )
    ),
    Field(
      name = "playSong",
      fieldType = OptionType(StringType),
      arguments = tokenArg :: songIdArg :: Nil,
      resolve = context => UserService.playSong(
        context.args.arg[Long]("token"),
        context.args.arg[Long]("songId")
      )
    )
  )
}

case class UserContext(context: UserService)
