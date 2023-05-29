package controllers
import graphql.{AlbumContext, GroupContext, GroupSchema, MainContext, MainSchema, SingerContext, SongContext, SongSchema}
import play.api.libs.json._
import play.api.mvc._
import sangria.execution._
import sangria.marshalling.playJson._
import sangria.parser.{QueryParser, SyntaxError}
import service.GroupService

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}



@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Redirect(routes.HomeController.graphql)
  }

  def graphql: Action[JsValue] = Action.async(parse.json) { request =>
    val query = (request.body \ "query").as[String]
    val operation = (request.body \ "operationName").asOpt[String]
    val variables = (request.body \ "variables").toOption.flatMap {
      case JsString(vars) => Some(parseVariables(vars))
      case obj: JsObject => Some(obj)
      case _ => None
    }
    executeGraphQLQuery(query, variables, operation)
  }

  def parseVariables(variables: String) =
    if (variables.trim == "" || variables.trim == "null") Json.obj() else Json.parse(variables).as[JsObject]

  def executeGraphQLQuery(query: String, vars: Option[JsObject], op: Option[String]) =
    QueryParser.parse(query) match {
      case Success(queryAst) => Executor.execute(
        schema = MainSchema(songSchema = SongSchema.songSchema, albumSchema = ???, singerSchema = ???, groupSchema = GroupSchema.groupSchema),
        queryAst = queryAst,
        userContext = MainContext,
        operationName = op,
        variables = vars getOrElse Json.obj())
        .map(Ok(_)).recover {
        case error: QueryAnalysisError => BadRequest(error.resolveError)
        case error: ErrorWithResolver => InternalServerError(error.resolveError)
      }

      case Failure(error: SyntaxError) =>
        Future.successful(BadRequest(Json.obj(
          "syntaxError" -> error.getMessage,
          "locations" -> Json.arr(Json.obj(
            "line" -> error.originalError.position.line,
            "column" -> error.originalError.position.column)
          )
        )))
      case Failure(error) =>
        throw error
    }
}
