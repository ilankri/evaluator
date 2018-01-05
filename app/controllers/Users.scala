package controllers

class Users(cc: UserControllerComponents) extends UserAbstractController(cc) {
  def home = TODO

  def signout = userAction { request =>
    Redirect(routes.HomeController.signinPage).withSession(
      request.session - cc.userIdKey
    )
  }

}
