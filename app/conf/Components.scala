package conf

import play.api.{ controllers => _, _ }
import play.api.mvc._

import router.Routes

class Components(context: ApplicationLoader.Context)
  extends BuiltInComponentsFromContext(context)
  with play.filters.HttpFiltersComponents
  with controllers.AssetsComponents {
  val database = db.MockDb(10, 10)

  lazy val messagesAction =
    new DefaultMessagesActionBuilderImpl(
      new BodyParsers.Default(playBodyParsers), new i18n.DefaultMessagesApi)

  lazy val homeController =
    new controllers.HomeController(messagesAction, controllerComponents,
      database)

  lazy val userController =
    new controllers.Users(controllerComponents, database)

  lazy val submissionController =
    new controllers.Submissions(controllerComponents, database)

  lazy val router =
    new Routes(httpErrorHandler, homeController, userController,
      submissionController, assets)
}
