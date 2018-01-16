package controllers

import play.api.mvc._

class Tasks(cc: AppControllerComponents) extends TaskAbstractController(cc) {
  def creationForm(kind: String) = evaluatorAction { implicit request =>
    Ok(TaskForm.creationPage(TaskForm.Mcq))
  }

  def form(taskId: Long) = taskMemberAction(taskId) { implicit request =>
    request.task.content match {
      case mcq @ models.Mcq(_) =>
        Ok(views.html.mcqDeliveryForm(McqDeliveryForm.form, mcq.questions))
    }

  }

  def create(kind: String) = evaluatorAction { implicit request =>
    val validate = TaskForm.validator(TaskForm.Mcq)
    validate(
      AppResults.todo,
      Redirect(routes.Users.ownedTasks(request.user.id)),
      cc.db
    )
  }

  def read(taskId: Long) = TODO

  def readAll = workerAction { implicit request: WorkerRequest[AnyContent] =>
    Ok(views.html.allTasks(cc.db.readAllTasks))
  }

  def deliverables(taskId: Long) = taskOwnerAction(taskId) { implicit request =>
    Ok(views.html.deliverables(request.task.deliverables))
  }

  /* TODO: "Htmlify" the rendering.  */
  def deliverable(taskId: Long, deliverableId: Long) =
    taskOwnerAction(taskId) { implicit request =>
      Ok(request.task.deliverables.find(_.id == deliverableId).toString)
        .as(HTML)
    }

  def register(id: Long) = taskWorkerAction(id) { request =>
    request.worker register request.task
    Ok
  }

  def unregister(id: Long) = taskMemberAction(id) { request =>
    request.worker unregister request.task
    Ok
  }

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
