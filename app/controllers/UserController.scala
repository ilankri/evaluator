package controllers

class UserController(cc: AppControllerComponents)
  extends UserAbstractController(cc) {
  def home = userAction { request => Ok(views.html.home(request.user)) }

  def signout = userAction { request =>
    Redirect(routes.HomeController.signinPage).withSession(
      request.session - cc.userIdKey
    )
  }

  def tasks = workerAction { implicit request =>
    Ok(views.html.tasks("My tasks", ???))
  }

  def ownedTasks = evaluatorAction { implicit request =>
    Ok(views.html.tasks("My owned tasks", ???))
  }
}
