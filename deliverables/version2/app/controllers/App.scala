package controllers

import play.api._
import play.api.mvc._

import router.Routes

import db._

class AppControllerComponents(
    messagesActionBuilder: mvc.MessagesActionBuilder,
    actionBuilder: mvc.DefaultActionBuilder,
    parsers: mvc.PlayBodyParsers,
    messagesApi: i18n.MessagesApi,
    langs: i18n.Langs,
    fileMimeTypes: http.FileMimeTypes,
    executionContext: concurrent.ExecutionContext,
    val db: Db,
    val userIdKey: String)
  extends mvc.DefaultMessagesControllerComponents(
    messagesActionBuilder,
    actionBuilder, parsers, messagesApi, langs, fileMimeTypes, executionContext)

class AppComponents(context: ApplicationLoader.Context)
  extends BuiltInComponentsFromContext(context)
  with play.filters.HttpFiltersComponents
  with AssetsComponents {

  val messagesAction =
    new DefaultMessagesActionBuilderImpl(
      new BodyParsers.Default(playBodyParsers), messagesApi)

  override lazy val controllerComponents =
    new AppControllerComponents(
      messagesAction,
      Action,
      playBodyParsers,
      messagesApi,
      langs,
      fileMimeTypes,
      executionContext,
      db.MockDb(10),
      "id")

  lazy val appController = new AppController(controllerComponents)

  lazy val userController = new Users(controllerComponents)

  lazy val taskController = new Tasks(controllerComponents)

  lazy val router =
    new Routes(httpErrorHandler, appController, userController, taskController,
      assets)
}

object AppResults {
  val unauthorized =
    Results.Unauthorized(<h1>Unauthorized</h1>).as(http.ContentTypes.HTML)

  val notFound =
    Results.NotFound(<h1>Not Found</h1>).as(http.ContentTypes.HTML)

  val todo =
    Results.NotImplemented(<h1>TODO</h1>).as(http.ContentTypes.HTML)
}
