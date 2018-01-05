package controllers

class Users(cc: UserControllerComponents) extends UserAbstractController(cc) {
  def home = userAction { request => Ok(views.html.home(request.user)) }

  def signout = userAction { request =>
    Redirect(routes.HomeController.signinPage).withSession(
      request.session - cc.userIdKey
    )
  }

}
