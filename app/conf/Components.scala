package conf

import play.api.{ controllers => _, _ }
import play.api.mvc._

import router.Routes

class Components(context: ApplicationLoader.Context)
  extends BuiltInComponentsFromContext(context)
  with play.filters.HttpFiltersComponents
  with controllers.AssetsComponents {
  val database = db.MockDb(10, 10)

  val messagesAction =
    new DefaultMessagesActionBuilderImpl(
      new BodyParsers.Default(playBodyParsers), messagesApi)

  override lazy val controllerComponents =
    new DefaultMessagesControllerComponents(
      messagesAction,
      Action,
      playBodyParsers,
      messagesApi,
      langs,
      fileMimeTypes,
      executionContext)

  lazy val homeController =
    new controllers.HomeController(controllerComponents, database)

  lazy val userController =
    new controllers.Users(controllerComponents, database)

  lazy val taskController =
    new controllers.Tasks(controllerComponents, database)

  lazy val router =
    new Routes(httpErrorHandler, homeController, userController,
      taskController, assets)
}
