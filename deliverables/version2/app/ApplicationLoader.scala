package loader

import play.api.{ controllers => _, _ }
import play.api.mvc._

import router.Routes

class ApplicationLoader extends play.api.ApplicationLoader {
  def load(context: ApplicationLoader.Context) =
    new Components(context).application
}

class Components(context: ApplicationLoader.Context)
  extends BuiltInComponentsFromContext(context)
  with play.filters.HttpFiltersComponents
  with controllers.AssetsComponents {

  val messagesAction =
    new DefaultMessagesActionBuilderImpl(
      new BodyParsers.Default(playBodyParsers), messagesApi)

  override lazy val controllerComponents =
    new controllers.AppControllerComponents(
      messagesAction,
      Action,
      playBodyParsers,
      messagesApi,
      langs,
      fileMimeTypes,
      executionContext,
      db.MockDb(10),
      "id")

  lazy val appController = new controllers.AppController(controllerComponents)

  lazy val userController = new controllers.Users(controllerComponents)

  lazy val taskController = new controllers.Tasks(controllerComponents)

  lazy val router =
    new Routes(httpErrorHandler, appController, userController, taskController,
      assets)
}
