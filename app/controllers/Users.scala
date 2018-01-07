package controllers

class Users(cc: AppControllerComponents) extends UserAbstractController(cc) {
  def read(id: Long) = userAction(id) { request =>
    Ok(views.html.home(request.user))
  }

  def tasks(id: Long) = workerAction(id) { implicit request =>
    Ok(views.html.tasks("My tasks", ???))
  }

  def ownedTasks(id: Long) = evaluatorAction(id) { implicit request =>
    Ok(views.html.tasks("My owned tasks", ???))
  }
}
