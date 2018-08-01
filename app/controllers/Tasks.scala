package controllers

import play.api.mvc._

/** This controller handles the tasks (creation, delivery, ...).  */
class Tasks(cc: AppControllerComponents) extends TaskAbstractController(cc) {
  /** Displays a page for creating a new task of the given type.  */
  def creationForm(kind: String) = evaluatorAction { implicit request =>
    Ok(TaskForm.creationPage(TaskForm.Kind.fromString(kind)))
  }

  /** Displays a page for delivering the task with the given ID.  */
  def form(taskId: Long) = taskMemberAction(taskId) { implicit request =>
    request.task.content match {
      case mcq @ models.Mcq(_) =>
        Ok(views.html.mcqDeliveryForm(McqDeliveryForm.form, mcq.questions))
    }
  }

  /** Performs the creation of a task.  */
  def create(kind: String) = evaluatorAction { implicit request =>
    val validate = TaskForm.validator(TaskForm.Kind.fromString(kind))
    validate(
      AppResults.todo,
      Redirect(routes.Users.ownedTasks(request.user.id)),
      cc.db
    )
  }

  def read(taskId: Long) = TODO

  /** Displays a page with all the submitted tasks.  */
  def readAll = workerAction { implicit request: WorkerRequest[AnyContent] =>
    Ok(views.html.allTasks(cc.db.readAllTasks))
  }

  /**
    * Displays a page with all the deliverables submitted for the given
    * task.
    */
  def deliverables(taskId: Long) = taskOwnerAction(taskId) { implicit request =>
    Ok(views.html.deliverables(request.task.deliverables))
  }

  /** Displays a specific deliverable for the given task.  */
  def deliverable(taskId: Long, deliverableId: Long) =
    taskOwnerAction(taskId) { implicit request =>
      /* TODO: "Htmlify" the rendering.  */
      Ok(request.task.deliverables.find(_.id == deliverableId).toString)
        .as(HTML)
    }

  /**
    * Performs the registration of the signed-in user for the task with
    * the given ID.
    */
  def register(id: Long) = taskWorkerAction(id) { request =>
    request.worker register request.task
    Ok
  }

  /**
    * Performs the unregistration of the signed-in user for the task with
    * the given ID.
    */
  def unregister(id: Long) = taskMemberAction(id) { request =>
    request.worker unregister request.task
    Ok
  }

  /**
    * Performs the submission of a deliverable for the task with the given
    * ID.
    */
  def deliver(taskId: Long) = taskMemberAction(taskId) { implicit request =>
    request.task.content match {
      case _: models.Mcq =>
        McqDeliveryForm.deliver(
          AppResults.todo,
          Redirect(routes.Users.tasks(request.worker.id))
        )
    }
  }

  def delete(taskId: Long) = taskOwnerAction(taskId) { AppResults.todo }
}
