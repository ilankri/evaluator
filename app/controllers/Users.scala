package controllers

/**
  * This controller is responsible for displaying of user-specific
  * information.
  */
class Users(cc: AppControllerComponents) extends UserAbstractController(cc) {
  /** Displays the home page of the user with the given ID.  */
  def read(id: Long) = authUserAction(id) { request =>
    Ok(views.html.home(request.user))
  }

  /** Displays the tasks the user with the given ID is registered for.  */
  def tasks(id: Long) = authWorkerAction(id) { implicit request =>
    Ok(views.html.tasks(request.user.tasks))
  }

  /** Displays the tasks owned by the user with the given ID.  */
  def ownedTasks(id: Long) = authEvaluatorAction(id) { implicit request =>
    Ok(views.html.ownedTasks(request.user.submittedTasks))
  }
}
