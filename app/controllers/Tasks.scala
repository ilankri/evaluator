package controllers

import play.api.mvc._

class Tasks(cc: AppControllerComponents) extends TaskAbstractController(cc) {
  def creationForm = TODO

  def form(id: Long) = TODO

  def create = TODO

  def read(id: Long) = TODO

  def readAll = workerAction { implicit request: WorkerRequest[AnyContent] =>
    Ok(views.html.allTasks(cc.db.readAllTasks))
  }

  def deliverables(id: Long) = TODO

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

  def deliver(id: Long) = TODO

  def delete(id: Long) = TODO
}
