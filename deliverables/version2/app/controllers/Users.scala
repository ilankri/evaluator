package controllers

class Users(cc: AppControllerComponents) extends UserAbstractController(cc) {
  def read(id: Long) = authUserAction(id) { request =>
    Ok(views.html.home(request.user))
  }

  def tasks(id: Long) = authWorkerAction(id) { implicit request =>
    Ok(views.html.tasks(request.user.tasks))
  }

  def ownedTasks(id: Long) = authEvaluatorAction(id) { implicit request =>
    Ok(views.html.ownedTasks(request.user.submittedTasks))
  }
}
