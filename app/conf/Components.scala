package conf

import play.api.ApplicationLoader.Context
import router.Routes

class Components(context: Context)
  extends play.api.BuiltInComponentsFromContext(context)
  with play.filters.HttpFiltersComponents
  with controllers.AssetsComponents {
  val database = db.MockDb(10, 10)

  lazy val homeController =
    new controllers.HomeController(controllerComponents)

  lazy val userController =
    new controllers.Users(controllerComponents, database)

  lazy val submissionController =
    new controllers.Submissions(controllerComponents, database)

  lazy val router =
    new Routes(httpErrorHandler, homeController, userController,
      submissionController, assets)
}
