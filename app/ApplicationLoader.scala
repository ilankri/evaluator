import play.api.ApplicationLoader.Context

class ApplicationLoader extends play.api.ApplicationLoader {
  def load(context: Context) = new conf.Components(context).application
}
