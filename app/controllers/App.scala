package controllers

import play.api._
import play.api.mvc._

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

abstract class AppAbstractController(cc: AppControllerComponents)
  extends MessagesAbstractController(cc) {
  def userAction =
    Action andThen new UserAction(
      cc.db,
      cc.userIdKey,
      Redirect(routes.HomeController.signinForm),
      cc.executionContext
    )

  def workerRefiner =
    new WorkerAction(AppResults.unauthorized, cc.executionContext)

  def evaluatorRefiner =
    new EvaluatorAction(AppResults.unauthorized, cc.executionContext)

  def workerAction = userAction andThen workerRefiner

  def evaluatorAction = userAction andThen evaluatorRefiner
}

object AppResults {
  val unauthorized =
    Results.Unauthorized(<h1>Unauthorized</h1>).as(http.ContentTypes.HTML)
}
