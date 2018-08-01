class ApplicationLoader extends play.api.ApplicationLoader {
  def load(context: play.api.ApplicationLoader.Context) =
    new controllers.AppComponents(context).application
}
