import play.api.ApplicationLoader.Context
import router.Routes

class ApplicationLoader extends play.api.ApplicationLoader {
  def load(context: Context) = new Components(context).application

  private class Components(context: Context)
    extends play.api.BuiltInComponentsFromContext(context)
    with play.filters.HttpFiltersComponents
    with controllers.AssetsComponents {
    lazy val homeController =
      new controllers.HomeController(controllerComponents)

    lazy val router =
      new Routes(httpErrorHandler, homeController, assets)
  }
}
