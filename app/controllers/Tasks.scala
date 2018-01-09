package controllers

import play.api.mvc._

class Tasks(cc: AppControllerComponents) extends TaskAbstractController(cc) {
  def creationForm = evaluatorAction { AppResults.todo }

  def form(taskId: Long) = taskMemberAction(taskId) { implicit request =>
    request.task.content match {
      case mcq @ models.Mcq(_) =>
        Ok(views.html.mcqDeliveryForm(McqDeliveryForm.form, mcq.questions))
    }

  }

  def create = evaluatorAction { AppResults.todo }

  def read(taskId: Long) = TODO

  def readAll = workerAction { implicit request: WorkerRequest[AnyContent] =>
    Ok(views.html.allTasks(cc.db.readAllTasks))
  }

  def deliverables(taskId: Long) = taskOwnerAction(taskId) { request =>
    AppResults.todo
  }

  def register(taskId: Long, userId: Long) =
    authTaskWorkerAction(taskId, userId) { request =>
      request.worker register request.task
      Ok
    }

  def unregister(taskId: Long, userId: Long) =
    authTaskWorkerAction(taskId, userId) { request =>
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
